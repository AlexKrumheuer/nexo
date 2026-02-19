package com.example.nexo.dto.auth;

import com.example.nexo.entity.user.UserRole;

public record UserResponseDTO(
    String username,
    String email,
    UserRole userRole
) {
    
}
