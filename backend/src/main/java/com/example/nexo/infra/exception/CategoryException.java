package com.example.nexo.infra.exception;

import org.springframework.http.HttpStatus;

public class CategoryException extends RuntimeException {

    private final HttpStatus status;

    public CategoryException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public CategoryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}