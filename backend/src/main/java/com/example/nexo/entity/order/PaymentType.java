package com.example.nexo.entity.order;

public enum PaymentType {
    PIX("pix"),
    CREDIT_CARD("credit"),
    DEBIT_CARD("debit"),
    BOLETO("boleto");

    private final String method;

    PaymentType(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
