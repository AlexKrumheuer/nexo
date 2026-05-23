package com.example.nexo.entity.product;

public enum OrderStatus {

    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
