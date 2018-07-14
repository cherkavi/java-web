package com.cherkashyn.vitalii.bpmnui.core.ui.common;


import com.cherkashyn.vitalii.bpmnui.core.domain.User;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;

import javax.annotation.PostConstruct;

@VaadinSessionScope
@SpringComponent
public class Exchange {
    public static String ORDERID_KEY="order_id";
    public static String STATUS_KEY="status";
    public enum Status{
        client_confirmed,
        ready_for_collecting,
        ready_for_shipment,
        shipped
    }

    public User user;
    public String processInstanceId;

    public Exchange(){
        // TODO remove me
        System.out.println(">>> create Exchange");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println(">>> post construct");
    }
}
