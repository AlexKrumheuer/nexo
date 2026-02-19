package com.example.nexo.dto.auth;

public record UpdateUserDto(String username, String password, String profilePictureUrl, String email) {
    
}