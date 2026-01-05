package com.zchat.service;

import com.zchat.dto.CreateUserDto;
import com.zchat.dto.UpdateUserDto;
import com.zchat.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.zchat.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;
import com.zchat.entity.Role;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserDto createUserDto) {

        var entity = new User(
                null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                createUserDto.role(),
                null);

        return userRepository.save(entity);
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
        user.setProfilePictureUrl(updateUserDto.profilePictureUrl());
        user.setEmail(updateUserDto.email());

        return userRepository.save(user);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}