package com.cherkashyn.vitalii.bpmnui.core.ui.view.common;

import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmTaskUi;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProcessNotFound extends BpmTaskUi {

    @Override
    protected com.vaadin.ui.Component getRootComponent() {
        HorizontalLayout layout = new HorizontalLayout();
        Label label = new Label(this.caption.get("bpm.label.process-not-found"));
        label.setWidth("100%");

        layout.addComponent(label);
        layout.setSizeFull();
        return layout;
    }

    @Override
    protected void complete() {
    }
}
