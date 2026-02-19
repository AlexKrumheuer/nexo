package com.example.nexo.entity.user;

public enum SellerStatus {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    SUSPENDED("SUSPEND"),
    BANNED("BANNED");

    private final String status;

    SellerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

