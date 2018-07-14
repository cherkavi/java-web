package com.cherkashyn.vitalii.bpmnui.custom;

import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmTaskUi;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Scope("prototype")
@org.springframework.stereotype.Component("CreateOrder")
public class CreateOrder extends BpmTaskUi {

    private TextField orderNumber;


    @Override
    protected Component getRootComponent() {
        orderNumber = new TextField();
        orderNumber.setCaption(caption.get("CreateOrder.label.orderid"));

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(orderNumber);
        layout.addComponent(buildButtonSetOrderNumber());

        layout.setMargin(true);
        layout.setSpacing(true);
        return layout;
    }

    private Component buildButtonSetOrderNumber() {
        return new Button(
                caption.get("CreateOrder.button.set-order-number"),
                clickEvent -> complete()
        );
    }

    protected void complete() {
        String orderNumberValue = StringUtils.trimToNull(orderNumber.getValue());
        if(orderNumberValue==null){
            Notification.show(caption.get("CreateOrder.message.order-is-empty"), Notification.Type.WARNING_MESSAGE);
            return;
        }
        if(isOrderExists(orderNumberValue)){
            Notification.show(caption.get("CreateOrder.message.order-already-exists"), Notification.Type.WARNING_MESSAGE);
            return;
        }
        this.completeUserTask("CreateOrder", new ImmutablePair<>(Exchange.ORDERID_KEY, orderNumberValue));
    }


    private Map<String, Object> buildVariables(String orderId){
        Map<String, Object> variables = new HashMap<>();
        variables.put(Exchange.ORDERID_KEY, orderId);
        return variables;
    }

    private boolean isOrderExists(String orderNumberValue) {
        // TODO check Runtime, check History
        return false;
    }

}
