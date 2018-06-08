package com.cherkashyn.vitalii.bpmnui.core.ui;

import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.ProcessListView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringUI
@SpringComponent
@Theme("runo")
public class MainUIDisplay extends UI {
    private static final Logger LOG = LoggerFactory.getLogger(MainUIDisplay.class);

    GridLayout root;

    @Autowired
    Caption caption;

    @Autowired
    ApplicationContext context;

    @Override
    protected void init(VaadinRequest request) {
        init();
    }

    public void init(){
        this.getPage().setTitle(caption.get("title"));
        buildRootElement();
        this.setRoot(ProcessListView.class);
    }

    private void buildRootElement(){
        this.root = new GridLayout(1,1);
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        root.setSizeFull();
        this.setContent(this.root);
        this.setSizeFull();
    }

    public void setRoot(Class<? extends Component> clazz) {
        Component component = context.getBean(clazz);
        if(component==null){
            Notification.show("Can't find window", Notification.Type.ERROR_MESSAGE);
            return;
        }
        setRoot(component);
    }

    public void setRoot(Component component) {
        root.removeAllComponents();
        root.addComponent(component);
        root.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    }

}
