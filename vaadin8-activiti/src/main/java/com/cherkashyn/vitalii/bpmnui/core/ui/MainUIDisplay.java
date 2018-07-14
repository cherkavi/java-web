package com.cherkashyn.vitalii.bpmnui.core.ui;

import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.ErrorView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@SpringUI
@SpringViewDisplay
@SpringComponent
@Theme("valo")
public class MainUIDisplay extends UI implements ViewDisplay, ContentController{
    private static final Logger LOG = LoggerFactory.getLogger(MainUIDisplay.class);

    Panel root;

    @Autowired
    Caption caption;

    @Autowired
    ApplicationContext context;

    @Override
    protected void init(VaadinRequest request) {
        init();
    }

    @PostConstruct
    public void init(){
        this.getPage().setTitle(caption.get("title"));
        if(this.getNavigator()!=null){
            this.getNavigator().setErrorView(ErrorView.class);
        }
        buildRootElement();
    }

    private void buildRootElement(){
        HorizontalLayout layout = new HorizontalLayout();
        // layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        this.setContent(layout);
        this.root =new Panel();
        layout.addComponentsAndExpand(root);
        root.setSizeFull();
    }

    @Override
    public void setRoot(Class<? extends Component> clazz) {
        Component component = context.getBean(clazz);
        if(component==null){
            LOG.error("BPMN diagram error, can't find window by class: "+clazz.getName());
            Notification.show("Can't find window", Notification.Type.ERROR_MESSAGE);
            return;
        }
        setRoot(component);
    }

    @Override
    public void setRoot(Component component) {
        root.setContent(component);
    }

    @Override
    public void showView(View view) {
        root.setContent((Component)view);
    }

}
