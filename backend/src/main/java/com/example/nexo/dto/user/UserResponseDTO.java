package com.example.nexo.dto.user;


import com.example.nexo.entity.user.UserRole;

public record UserResponseDTO(
    String username,
    String email,
    UserRole userRole,
    String bannerUrl,
    String perfilUrl
) {
    
}
