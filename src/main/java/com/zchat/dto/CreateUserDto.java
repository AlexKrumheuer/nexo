package com.zchat.dto;

import com.zchat.entity.Role;

public record CreateUserDto(
        String email,
        String password,
        String username,
        Role role
    ) {
}
