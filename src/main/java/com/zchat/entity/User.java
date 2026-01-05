package com.zchat.entity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    private String email;
    private String password;
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long id, String username, String email, String password, Role role, Object role1) {
    }
}