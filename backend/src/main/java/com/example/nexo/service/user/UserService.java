package com.example.nexo.service.user;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.nexo.dto.auth.UpdateUserDto;
import com.example.nexo.dto.auth.UserResponseDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.entity.user.UserRole;
import com.example.nexo.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO turnSeller(User user) {
        user.setRole(UserRole.SELLER);
        userRepository.save(user);

        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getRole());
    }


    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User updateUserById(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setUsername(updateUserDto.username());
        user.setPassword(updateUserDto.password());
        user.setEmail(updateUserDto.email());

        return userRepository.save(user);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}