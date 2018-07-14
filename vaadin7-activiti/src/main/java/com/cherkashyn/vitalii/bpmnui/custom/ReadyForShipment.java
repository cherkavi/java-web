package com.cherkashyn.vitalii.bpmnui.custom;

import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmTaskUi;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vaadin.ui.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@org.springframework.stereotype.Component("ReadyForShipment")
public class ReadyForShipment extends BpmTaskUi {

    private ComboBox orderReady;

    @Override
    protected Component getRootComponent() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("ReadyForShipment: "));
        layout.addComponent(new Label("user: "+this.exchange.user));
        layout.addComponent(new Label("processInstanceId: "+this.exchange.processInstanceId));

        orderReady = new ComboBox("Is documents ready? ");
        orderReady.addItem(Boolean.TRUE.toString());
        orderReady.addItem(Boolean.FALSE.toString());
        layout.addComponent(orderReady);
        layout.addComponent(new Button("complete", (Button.ClickListener) clickEvent -> complete()));
        return layout;
    }

    @Override
    protected void complete() {

        if(orderReady.getValue()==null){
            Notification.show("select decision", Notification.Type.WARNING_MESSAGE);
            return;
        }
        this.completeUserTask("ReadyForShipment",
                 new ImmutablePair<>(Exchange.STATUS_KEY, Exchange.Status.ready_for_shipment.name())
                ,new ImmutablePair<>("warehouseOrderReady", Boolean.parseBoolean((String)orderReady.getValue()))
        );
    }

}
