package com.example.nexo.dto;

import com.example.nexo.entity.UserRole;

public record UserResponseDTO(
    String username,
    String email,
    UserRole userRole
) {
    
}
