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
@org.springframework.stereotype.Component("ReadyForCollecting")
public class ReadyForCollecting extends BpmTaskUi {
    @Override
    protected Component getRootComponent() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("ReadyForCollecting: "));
        layout.addComponent(new Label("user: "+this.exchange.user));
        layout.addComponent(new Label("processInstanceId: "+this.exchange.processInstanceId));
        layout.addComponent(new Button("complete", (Button.ClickListener) clickEvent -> complete()));
        return layout;
    }

    @Override
    protected void complete() {
        this.completeUserTask("ReadyForCollecting", new ImmutablePair<>(Exchange.STATUS_KEY, Exchange.Status.ready_for_collecting.name()));
    }

}
