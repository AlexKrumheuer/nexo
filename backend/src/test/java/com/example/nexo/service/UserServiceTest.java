package com.example.nexo.service;

import com.example.nexo.dto.auth.UpdateUserDto;
import com.example.nexo.entity.user.User;
import com.example.nexo.repository.user.UserRepository;
import com.example.nexo.service.user.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void mustReturnUserWhenExists (){ //Criando usuario fake e buscando por id (cenario onde o usuario existe)
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L,result.get().getId());
    }

    @Test
    void shouldReturnOptionalEmptyWhenItDoesNotExist(){ // cenario onde usuario é inexistente
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void mustReturnUserList () {
        List<User> users = List.of(new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.listUsers();

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnEmptyList () {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = userService.listUsers();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateUserWhenExists () {
        User user = new User();
        user.setId(1L);

        UpdateUserDto dto = new UpdateUserDto(
                "newUser",
                "123",
                "teste@gmail.com"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        User result = userService.updateUserById(1L, dto);

        assertEquals("newUser", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals("teste@gmail.com", result.getEmail());

        verify(userRepository).save(user);
    }

    @Test
    void throws404WhenUserDoesNotExist () {
        UpdateUserDto dto = new UpdateUserDto(
                "user",
                "1234567",
                "email@gmail.com"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> userService.updateUserById(1L, dto)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
    }

    @Test
    void shouldDeleteUserById () {
        userService.deleteById(1L);

        verify(userRepository).deleteById(1L);
    }
}