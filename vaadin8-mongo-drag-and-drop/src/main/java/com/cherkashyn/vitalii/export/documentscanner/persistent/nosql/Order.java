package com.cherkashyn.vitalii.export.documentscanner.persistent.nosql;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;


public class Order {
    @Id
    private String id;

    public String orderId;

    public List<OrderFileItem> files = new ArrayList<>();

    public boolean wasSaved(){
        return id!=null;
    }

    public Order() {
    }

    public Order(String uniqueOrderId) {
        this.orderId = uniqueOrderId;
    }

    public Order(String uniqueOrderId, List<OrderFileItem> files) {
        this.orderId = uniqueOrderId;
        this.files = files;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + this.orderId+ '\'' +
                ", files=" + files +
                '}';
    }
}
