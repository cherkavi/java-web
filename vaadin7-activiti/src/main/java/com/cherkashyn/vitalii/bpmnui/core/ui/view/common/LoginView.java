package com.cherkashyn.vitalii.bpmnui.core.ui.view.common;

import com.cherkashyn.vitalii.bpmnui.core.domain.User;
import com.cherkashyn.vitalii.bpmnui.core.repository.UserRepository;
import com.cherkashyn.vitalii.bpmnui.core.ui.ContentController;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist.ProcessListView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@VaadinSessionScope // the same as Exchange
@SpringComponent
public class LoginView extends GridLayout{

    @Autowired
    ContentController contentController;

    @Autowired
    Caption caption;

    @Autowired
    UserRepository repository;

    @Autowired
    Exchange exchange;

    public LoginView(){
        super(1,1);
    }

    @PostConstruct
    void init() {
        FormLayout form = new FormLayout();
        // form.setSizeFull();
        // form.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        TextField login = new TextField("Login");
        login.setIcon(VaadinIcons.USER);
        // login.setRequiredIndicatorVisible(true);
        form.addComponent(login);

        PasswordField password = new PasswordField("Password");
        password.setIcon(VaadinIcons.PASSWORD);
        form.addComponent(password);
        // normally comes from validation by Binder
        // password.setComponentError(new UserError("Not found"));

        Button buttonEnter = new Button(caption.get("loginview.button.enter"));
        buttonEnter.setIcon(VaadinIcons.SEARCH);
        buttonEnter.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        buttonEnter.addClickListener(e -> onClickButtonEnter(login.getValue(), password.getValue()));
        form.addComponent(buttonEnter);

        this.addComponent(form);
        this.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
        // this.setSizeFull();
    }

    private void onClickButtonEnter(String login, String password) {
        if(StringUtils.trimToNull(login)==null){
            Notification.show(caption.get("loginview.message.emptyLogin"), Notification.Type.WARNING_MESSAGE);
            return;
        }
        if(StringUtils.trimToNull(password)==null){
            Notification.show(caption.get("loginview.message.emptyPassword"), Notification.Type.WARNING_MESSAGE);
            return;
        }

        User user=repository.getUser(login,password);
        if(user==null){
            Notification.show(caption.get("loginview.message.usernotfound"), Notification.Type.WARNING_MESSAGE);
            return;
        }
        if(user.getProcesses().isEmpty()){
            Notification.show(caption.get("loginview.message.noprocessfound"), Notification.Type.WARNING_MESSAGE);
            this.addComponent(new Label());
            return;
        }

        this.exchange.user = user;
        if(this.exchange.user.getProcesses().size()==1){
            // bpmAware.showScreen(user);
            this.contentController.setRoot(ProcessListView.class);
        }else{
            Notification.show("Not possible to use more than one process !!!", Notification.Type.ERROR_MESSAGE);
        }
    }

}
