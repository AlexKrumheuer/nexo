package com.example.nexo.entity.user;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    SELLER("ROLE_SELLER"),
    USER("ROLE_USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
