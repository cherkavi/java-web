package com.cherkashyn.vitalii.bpmnui.custom;

import com.cherkashyn.vitalii.bpmnui.core.ui.view.bpmframe.BpmTaskUi;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@org.springframework.stereotype.Component("CompleteStatus")
public class CompleteStatus extends BpmTaskUi {
    @Override
    protected Component getRootComponent() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("CompleteStatus"));
        layout.addComponent(new Label("orderId: "+this.exchange.currentProcess.getOrderId()));
        layout.addComponent(new Label("definitionId: "+this.exchange.currentProcess.getProcessDefinitionId()));
        layout.addComponent(new Label("instanceId: "+this.exchange.currentProcess.getProcessInstanceId()));
        layout.addComponent(new Label("creationDate: "+this.exchange.currentProcess.getCreationDate().toString()));
        return layout;
    }
}
