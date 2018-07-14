package com.cherkashyn.vitalii.bpmnui.core.ui;

import com.cherkashyn.vitalii.bpmnui.core.ui.ContentController;
import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmUIController;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.cherkashyn.vitalii.bpmnui.core.domain.User;
import com.cherkashyn.vitalii.bpmnui.core.repository.UserRepository;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.bpmframe.BpmTaskUi;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist.ProcessListView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
@SpringView(name="") // starting point of application
public class MainView extends BpmTaskUi implements View{


    @Override
    protected Component getRootComponent() {
        VerticalLayout layout = new VerticalLayout();
        final TextField login = new TextField();
        layout.addComponent(login);
        final PasswordField password = new PasswordField();
        layout.addComponent(password);
        Button buttonEnter = new Button(caption.get("loginview.button.enter"), FontAwesome.SEARCH);
        buttonEnter.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        buttonEnter.setSizeFull();
        buttonEnter.addClickListener(e -> onClickButtonEnter(login.getValue(), password.getValue()));
        layout.addComponent(buttonEnter);
        return layout;
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

        User user=userRepository.getUser(login,password);
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
