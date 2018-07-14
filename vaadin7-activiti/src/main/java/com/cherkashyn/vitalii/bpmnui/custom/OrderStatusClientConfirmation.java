package com.cherkashyn.vitalii.bpmnui.custom;

import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmTaskUi;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@org.springframework.stereotype.Component("OrderStatusClientConfirmation")
public class OrderStatusClientConfirmation extends BpmTaskUi {
    @Override
    protected Component getRootComponent() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("CompleteStatus: "));
        layout.addComponent(new Label("user: "+this.exchange.user));
        layout.addComponent(new Label("processInstanceId: "+this.exchange.processInstanceId));
        layout.addComponent(new Button("complete", (Button.ClickListener) clickEvent -> complete()));
        return layout;
    }

    @Override
    protected void complete() {
        this.completeUserTask("OrderStatusClientConfirmation", new ImmutablePair<>(Exchange.STATUS_KEY, Exchange.Status.client_confirmed.name()));
    }

}
