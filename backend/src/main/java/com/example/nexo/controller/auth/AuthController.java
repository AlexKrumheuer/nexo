package com.example.nexo.controller.auth;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nexo.dto.auth.CreateUserDto;
import com.example.nexo.dto.auth.LoginResponseDTO;
import com.example.nexo.dto.auth.LoginUserDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.service.user.AuthService;
import com.example.nexo.service.user.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;
    public AuthController(AuthService authService, TokenBlacklistService tokenBlacklistService) {
        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid CreateUserDto data) {
        try {
            User userSave = authService.createUser(data);
            return ResponseEntity.ok(userSave);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginUserDTO data) {
        try {
            LoginResponseDTO token = authService.loginUser(data);
            return ResponseEntity.ok(token);
        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Credentials");
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = recoverToken(request);
        if(token != null) {
            tokenBlacklistService.addTokenToBlacklist(token);
        }
        return ResponseEntity.ok().build();  
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    } 
    
    
    
}
