package com.example.nexo.infra.exception;

import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeException {

    private final HttpStatus status;

    public ProductException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ProductException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}