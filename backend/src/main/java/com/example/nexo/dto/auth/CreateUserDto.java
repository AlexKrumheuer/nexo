package com.example.nexo.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserDto(
        @NotBlank(message = "Username must not be blank") String username,
        @Email(message = "E-mail not valid") String email,
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*^#()\\[\\]{}|\\\\/+_.:;=~])[^\\s<>]{8,}$", message = "Password incorrect") String password
        ) {
}
