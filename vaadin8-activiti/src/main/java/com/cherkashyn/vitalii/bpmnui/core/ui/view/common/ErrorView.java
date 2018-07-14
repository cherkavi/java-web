package com.cherkashyn.vitalii.bpmnui.core.ui.view.common;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

@UIScope
@SpringView
public class ErrorView extends Label implements View {
    @PostConstruct
    void afterPropertiesSet() {
        setValue("Error view");
    }
}
