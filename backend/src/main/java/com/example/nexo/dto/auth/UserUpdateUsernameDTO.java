package com.example.nexo.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateUsernameDTO(
    @NotBlank(message="Username must not be blank")
    String username
) {
}
