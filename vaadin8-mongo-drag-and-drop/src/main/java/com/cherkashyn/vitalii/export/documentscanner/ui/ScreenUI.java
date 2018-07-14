package com.cherkashyn.vitalii.export.documentscanner.ui;

import com.cherkashyn.vitalii.export.documentscanner.ui.common.Caption;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@SpringViewDisplay
@SpringComponent
@Theme("valo")
public class ScreenUI extends UI implements ViewDisplay {

//    private Panel panel;
    HorizontalLayout layout;

    @Autowired
    Caption caption;

    @Override
    protected void init(VaadinRequest request) {
        this.getPage().setTitle(caption.get("title"));
        this.getNavigator().setErrorView(ErrorView.class);

//        panel = new Panel();
//        panel.setSizeFull();
        layout = new HorizontalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();

        //        Button button = new Button("Display other view");
        //        button.addClickListener(e -> getUI().getNavigator().navigateTo("another"));

        // VerticalLayout root = new VerticalLayout(button, layout);
        setContent(layout);
    }

    @Override
    public void showView(View view) {
//        panel.setContent((Component)view);
        layout.removeAllComponents();
        layout.addComponentsAndExpand((Component) view);
    }
}
