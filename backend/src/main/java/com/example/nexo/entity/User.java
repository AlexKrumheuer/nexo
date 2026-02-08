package com.example.nexo.entity;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String username;

    @Column(nullable = false, length = 500, unique = true)
    private String email;
    @Column(nullable = false, length = 500)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = UserRole.USER;
    }

    // Spring security methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
        return List.of(
            new SimpleGrantedAuthority("ROLE_ADMIN"), 
            new SimpleGrantedAuthority("ROLE_SELLER"), // Opcional: Se Admin também puder vender
            new SimpleGrantedAuthority("ROLE_USER")
        );
    }
    
        if (this.role == UserRole.SELLER) {
            return List.of(
                new SimpleGrantedAuthority("ROLE_SELLER"), 
                new SimpleGrantedAuthority("ROLE_USER")
            );
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}