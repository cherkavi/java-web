package com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist;

import com.cherkashyn.vitalii.bpmnui.core.ui.ContentController;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.activiti.engine.ProcessEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@UIScope
@SpringComponent()
public class ProcessNew extends VerticalLayout implements View{

    @Autowired
    ContentController contentController;

    @Autowired
    Caption caption;

    @Autowired
    Exchange exchange;

    @Autowired
    ProcessEngine processEngine;

    private String processDefinitionKey;

    public ProcessNew(){
    }

    private TextField orderNumber;

    @PostConstruct
    void init() {
        this.processDefinitionKey = getUserProcess();
        orderNumber = new TextField();
        orderNumber.setCaption(caption.get("processnewview.label.newprocess"));
        this.addComponent(orderNumber);
        this.addComponent(orderNumber);
        this.addComponent(new HorizontalLayout(buildButtonCancel(), buildButtonStartProcess()));

        this.setMargin(true);
        this.setSpacing(true);
    }

    private Component buildButtonStartProcess() {
        return new Button(
                caption.get("processnewview.button.start-process"),
                clickEvent -> startBpmProcess()
        );
    }

    private void startBpmProcess() {
        String orderNumberValue = StringUtils.trimToNull(orderNumber.getValue());
        if(orderNumberValue==null){
            Notification.show(caption.get("processnewview.message.order-is-empty"), Notification.Type.WARNING_MESSAGE);
            return;
        }
        if(isOrderExists(orderNumberValue)){
            Notification.show(caption.get("processnewview.message.order-already-exists"), Notification.Type.WARNING_MESSAGE);
            return;
        }

        processEngine.getRuntimeService().startProcessInstanceByKey(this.processDefinitionKey, buildVariables(orderNumberValue));

        this.contentController.setRoot(ProcessListView.class);
    }

    private Map<String, Object> buildVariables(String orderId){
        Map<String, Object> variables = new HashMap<>();
        variables.put(Exchange.ORDERID_KEY, orderId);
        return variables;
    }

    private boolean isOrderExists(String orderNumberValue) {
        // TODO check Runtime, check History
        return false;
    }

    private Button buildButtonCancel() {
        return new Button(
                caption.get("processnewview.button.cancel"),
                clickEvent -> this.contentController.setRoot(ProcessListView.class)
                );
    }

    /**
     * retrieve first process from the list
     * @return
     */
    private String getUserProcess() {
        return this.exchange.user.getProcesses().iterator().next();
    }

}
