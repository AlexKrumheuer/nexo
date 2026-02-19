package com.example.nexo.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDTO(
    @Email(message = "Invalid E-mail")String email,
    @NotBlank(message="Password must not be blank")String password
) {
    
}
