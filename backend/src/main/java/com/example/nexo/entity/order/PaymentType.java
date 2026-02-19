package com.example.nexo.entity.order;

public enum PaymentType {
    PIX("PIX"),
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    BOLETO("BOLETO");

    private final String method;

    PaymentType(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
