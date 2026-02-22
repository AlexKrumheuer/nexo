package com.example.nexo.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.nexo.dto.auth.UserResponseDTO;
import com.example.nexo.dto.auth.UserUpdateUsernameDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.service.user.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUserInfo(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.getUserInfo(user));
    }

    @PutMapping("/edit-username")
    public ResponseEntity<UserResponseDTO> putUsername(@RequestBody @Valid UserUpdateUsernameDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.updateUsername(dto, user));
    }

    @PostMapping("/me/banner-image")
    public ResponseEntity<UserResponseDTO> postBannerImage(
        @RequestParam("file") MultipartFile file, Authentication auth
    ) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.postBannerImage(file, user));
    }

    @PostMapping("/me/perfil-image")
    public ResponseEntity<UserResponseDTO> postPerfilImage(
        @RequestParam("file") MultipartFile file, Authentication auth
    ) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userService.postPerfilImage(file, user));
    }
    

}