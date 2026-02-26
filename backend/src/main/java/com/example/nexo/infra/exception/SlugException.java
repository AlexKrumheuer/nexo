package com.example.nexo.infra.exception;

import org.springframework.http.HttpStatus;

public class SlugException extends RuntimeException {

    private final HttpStatus status;

    public SlugException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public SlugException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}