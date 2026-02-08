package com.example.nexo.infra.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.nexo.dto.ApiErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalHandlerException {
    
    // Handle  errors related to Cart
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ApiErrorDTO> handleCartException(CartException ex, HttpServletRequest request) {
        
        ApiErrorDTO error = new ApiErrorDTO(
            LocalDateTime.now(),
            ex.getStatus().value(),
            ex.getStatus().getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    // Handle  errors related to Product
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ApiErrorDTO> handleProductException(ProductException ex, HttpServletRequest request) {
        
        ApiErrorDTO error = new ApiErrorDTO(
            LocalDateTime.now(),
            ex.getStatus().value(),
            ex.getStatus().getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(ex.getStatus()).body(error);
    }
    

    // Handle general Errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGeneralException(Exception ex, HttpServletRequest request) {
        ApiErrorDTO error = new ApiErrorDTO(
            LocalDateTime.now(),
            500,
            "Internal Server Error",
            "Unexpected error has occurred. Try again later",
            request.getRequestURI()
        );
        return ResponseEntity.status(500).body(error);
    }
}
