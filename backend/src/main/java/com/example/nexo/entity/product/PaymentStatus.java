package com.example.nexo.entity.product;

public enum PaymentStatus {
    AWAITING_PAYMENT("AWAITING_PAYMENT"),
    PAID("PAID"),
    FAILED("FAILED"),
    REFUNDED("REFUNDED"),
    CANCELLED("CANCELLED");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
