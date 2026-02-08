package com.example.nexo.controller;

import com.example.nexo.dto.UpdateUserDto;
import com.example.nexo.entity.User;

import com.example.nexo.service.TokenService;
import com.example.nexo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnUserWhenExists() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");

        when(userService.getUserById(1L))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/v1/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void shouldReturn404WhenUserDoesNotExist() throws Exception {
        when(userService.getUserById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUserList() throws Exception {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        when(userService.listUsers())
                .thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateUserWhenExists() throws Exception {
        UpdateUserDto dto = new UpdateUserDto(
                "newUser",
                "123",
                "email@gmail.com"
        );

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("newUser");
        updatedUser.setPassword("123");
        updatedUser.setEmail("email@gmail.com");

        when(userService.updateUserById(eq(1L), any(UpdateUserDto.class)))
                .thenReturn(updatedUser);

        mockMvc.perform(put("/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newUser"))
                .andExpect(jsonPath("$.email").value("email@gmail.com"));
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/v1/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService).deleteById(1L);
    }
}
