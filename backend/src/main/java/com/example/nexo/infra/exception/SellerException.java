package com.example.nexo.infra.exception;

import org.springframework.http.HttpStatus;

public class SellerException extends RuntimeException {

    private final HttpStatus status;

    public SellerException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public SellerException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}