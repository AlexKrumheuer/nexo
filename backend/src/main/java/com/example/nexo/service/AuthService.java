package com.example.nexo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nexo.dto.CreateUserDto;
import com.example.nexo.dto.LoginResponseDTO;
import com.example.nexo.dto.LoginUserDTO;
import com.example.nexo.entity.User;
import com.example.nexo.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public User createUser(CreateUserDto createUserDto) {
        String encryptedPassword = passwordEncoder.encode(createUserDto.password());
        User entity = new User(
                createUserDto.username(),
                createUserDto.email(),
                encryptedPassword);

        return userRepository.save(entity);
    }

    public LoginResponseDTO loginUser(LoginUserDTO loginUserDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginUserDTO.email(), loginUserDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        
        return new LoginResponseDTO(token);
    }
}
