package com.example.nexo.infra.exception;

import org.springframework.http.HttpStatus;

public class CartException extends RuntimeException {

    private final HttpStatus status;

    public CartException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public CartException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}