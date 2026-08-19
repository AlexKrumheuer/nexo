package com.example.nexo.entity.product;

public enum DeliveryStatus {

    PENDING_SELLER("PENDING_SELLER"),
    AWAITNG_SHIPMENT("AWAITNG_SHIPMENT"),
    SHIPPED("SHIPPED"),
    IN_TRANSIT("IN_TRANSIT"),
    DELIVERED("DELIVERED"),
    RETURNED("RETURNED"),
    CANCELLED("CANCELLED");

    private final String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
