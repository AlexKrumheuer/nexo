package com.example.nexo.config;

import com.example.nexo.entity.user.User;
import com.example.nexo.entity.user.UserRole;
import com.example.nexo.repository.user.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findUserByEmail("admin@nexo.com").isEmpty()) {
            
            User admin = new User(
                    "Admin Master",
                    "admin@nexo.com",
                    passwordEncoder.encode("admin123"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }
    }
}