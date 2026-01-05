package com.example.nexo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateSellerDTO(
    @NotBlank(message = "Company must not be blank")
    String companyName,

    @Email(message = "Invalid email")
    String email,

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*^#()\\[\\]{}|\\\\/+_.:;=~])[^\\s<>]{8,}$",
        message = "Password incorrect"
    )
    String password,
    
    String role
) {
    
}
