package com.cherkashyn.vitalii.export.documentscanner.ui.findorder;

import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.Order;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderRepository;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.Caption;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.Exchange;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.upload.TempStorageOutputStreamGenerator;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.upload.UploadReceiver;
import com.cherkashyn.vitalii.export.documentscanner.utility.BarCodeRecognizer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.Optional;

@SpringView(name="")
@UIScope
@SpringComponent
public class FindView extends VerticalLayout implements View {

    private static final Logger LOG = LoggerFactory.getLogger(FindView.class);

    private TextField textFieldNumber;

    @Autowired
    Caption caption;

    @Autowired
    SpringNavigator navigator;

    @Autowired
    Exchange shared;

    @Autowired
    OrderRepository repository;


    @Autowired
    BarCodeRecognizer barCodeRecognizer;

    @PostConstruct
    public void init(){
        this.setSizeFull();

        this.textFieldNumber= buildTextFiled();
        Button buttonEnter = buildFindButton();
        Upload buttonUpload = buildUploadButton();

        VerticalLayout layout = new VerticalLayout(textFieldNumber, buttonEnter, buttonUpload);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(layout);
    }

    private Button buildFindButton() {
        Button buttonEnter = new Button(caption.get("number.button.enter"), FontAwesome.SEARCH);
        buttonEnter.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        buttonEnter.setSizeFull();
        buttonEnter.addClickListener(e -> onClickButtonEnter());
        return buttonEnter;
    }

    private Upload buildUploadButton() {
        final Upload upload = new Upload();
        upload.setSizeFull();
        upload.setButtonCaption("upload file with BarCode");

        final UploadReceiver receiver = new UploadReceiver(new TempStorageOutputStreamGenerator());
        upload.setReceiver(receiver);
        upload.addSucceededListener(succeededEvent -> onUploadImage(receiver.getSavedFile().toFile()));
        upload.addFailedListener(failedEvent -> Notification.show("", "can't upload image", Notification.Type.ERROR_MESSAGE));
        return upload;
    }

    private void onUploadImage(File file) {
        List<String> values = barCodeRecognizer.recognize(file);
        if(values.isEmpty()){
            Notification.show("", "Can't find any BarCode on image", Notification.Type.HUMANIZED_MESSAGE);
            return;
        }

        if(values.size()>1){
            Notification.show("", "Image contains many codes", Notification.Type.WARNING_MESSAGE);
        }
        textFieldNumber.setValue(values.get(0));
    }

    private TextField buildTextFiled() {
        TextField textField = new TextField();
        textField.setSizeFull();// Width(100, Unit.PIXELS);
        textField.setPlaceholder(caption.get("number.textfield.advice"));
        textField.focus();
        return textField;
    }

    private void onClickButtonEnter() {
        final String orderId = this.textFieldNumber.getValue().trim();
        Optional<Order> order = repository.findByOrderId(orderId);
        if(order.isPresent()){
            shared.order = order.get();
            LOG.info("open existing order: "+this.textFieldNumber.getValue());
            navigator.navigateTo("upload");
        }else{
            ConfirmDialog.show(UI.getCurrent(),
                    "Nothing was found",
                    "Do you want to create new one ?",
                    "Yes", "No",
                    (ConfirmDialog.Listener) dialog -> {
                        if (dialog.isConfirmed()) {
                            shared.order = createNewOrder(orderId);
                            if(shared.order!=null){
                                LOG.info("open just have created order: "+orderId);
                                navigator.navigateTo("upload");
                            }
                        }else{
                            Notification.show("Code was not found:"+orderId, "Try to find again", Notification.Type.TRAY_NOTIFICATION);
                        }
                    });
        }
    }

    private Order createNewOrder(String orderId) {
        try{
            return this.repository.save(new Order(orderId));
        }catch(RuntimeException re){
            LOG.warn("can't create order with Id: "+orderId);
            Notification.show("can't create new order with id: "+orderId, Notification.Type.ERROR_MESSAGE);
            return null;
        }
    }

}
