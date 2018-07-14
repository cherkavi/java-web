package com.cherkashyn.vitalii.export.documentscanner.persistent.nosql;

import java.util.Objects;

public class OrderFileItem {
    public String name;
    public long size;

    public OrderFileItem(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public OrderFileItem() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderFileItem that = (OrderFileItem) o;
        return size == that.size &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, size);
    }
}
