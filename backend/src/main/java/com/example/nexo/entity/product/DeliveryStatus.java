package com.example.nexo.entity.product;

public enum DeliveryStatus {

    PENDING_SELLER("PENDING_SELLER"), // Confirmed
    AWAITING_SHIPMENT("AWAITING_SHIPMENT"), // Confirmed
    SHIPPED("SHIPPED"),
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
