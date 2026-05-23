package com.example.nexo.controller.product;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nexo.dto.product.OrderCreateDTO;
import com.example.nexo.dto.product.OrderResponseDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.service.product.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        
        OrderResponseDTO order = orderService.createOrder(dto, user);

        return ResponseEntity.ok(order);
    }
}
