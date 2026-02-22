package com.example.nexo.service.user;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.nexo.dto.auth.UserResponseDTO;
import com.example.nexo.dto.auth.UserUpdateUsernameDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.entity.user.UserRole;
import com.example.nexo.infra.exception.UserException;
import com.example.nexo.repository.user.UserRepository;
import com.example.nexo.service.ImageService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;

    public UserService(UserRepository userRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    public UserResponseDTO getUserInfo(User user) {
        User actualDataUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserException("User not found, invalid JWT Token", HttpStatus.NOT_FOUND));
        return mapUser(actualDataUser);
    }

    public UserResponseDTO turnSeller(User user) {
        user.setRole(UserRole.SELLER);
        userRepository.save(user);

        return mapUser(user);
    }

    public UserResponseDTO postBannerImage(MultipartFile file, User user) {
        User actualUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));
        String imageUrl = imageService.uploadImage(file);
        actualUser.setUserBannerImage(imageUrl);
        User userUpdated = userRepository.save(actualUser);

        return mapUser(userUpdated);
    }

     public UserResponseDTO postPerfilImage(MultipartFile file, User user) {
        User actualUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));
        String imageUrl = imageService.uploadImage(file);
        actualUser.setUserPerfilImage(imageUrl);
        User userUpdated = userRepository.save(actualUser);

        return mapUser(userUpdated);
    }

    public UserResponseDTO updateUsername(UserUpdateUsernameDTO dto, User user) {
        User actualUser = userRepository.findById(user.getId())
                .orElseThrow(()-> new UserException("User not found", HttpStatus.NOT_FOUND));
        if(actualUser.getUsername() != dto.username()) {
            actualUser.setUsername(dto.username());
        } else {
            throw new UserException("New username is equal to the current one", HttpStatus.CONFLICT);
        }

        User userUpdated = userRepository.save(actualUser);
        return mapUser(userUpdated);
    }


    public UserResponseDTO mapUser(User user) {
        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getRole(), user.getUserBannerImage(), user.getUserPerfilImage());
    }
}