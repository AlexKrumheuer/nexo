package com.example.nexo.dto.user;

import java.util.List;

import com.example.nexo.entity.user.UserRole;

public record UserResponseDetailedDTO(
    String username,
    String email,
    UserRole userRole,
    String bannerUrl,
    String perfilUrl,
    List<AddressResponseDTO> address
) {
    
}
