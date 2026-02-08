package com.example.nexo.dto;

import java.time.LocalDateTime;

public record ApiErrorDTO(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String message,
    String path
) {
    
}
