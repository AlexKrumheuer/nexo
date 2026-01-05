package com.example.nexo.entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

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
    }
}