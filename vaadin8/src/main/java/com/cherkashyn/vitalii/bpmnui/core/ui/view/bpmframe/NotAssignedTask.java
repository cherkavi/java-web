package com.cherkashyn.vitalii.bpmnui.core.ui.view.bpmframe;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NotAssignedTask extends BpmTaskUi {

    @Override
    protected com.vaadin.ui.Component getRootComponent() {
        VerticalLayout layout = new VerticalLayout();
        // layout.setComponentAlignment(new Label(this.caption.get("bpm.task-not-assigned")), Alignment.MIDDLE_CENTER);
        layout.addComponent(new Label(this.caption.get("bpm.task-not-assigned")));
        layout.setSizeFull();
        return layout;
    }
}
