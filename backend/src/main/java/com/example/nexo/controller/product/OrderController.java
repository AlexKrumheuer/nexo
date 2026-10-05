package com.example.nexo.controller.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nexo.dto.product.OrderCreateDTO;
import com.example.nexo.dto.product.OrderResponseDTO;
import com.example.nexo.dto.product.ProductResponseDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.service.product.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


// Endpoints related to orders

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<OrderResponseDTO> orders = orderService.getOrders(user);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderCode}")
    public ResponseEntity<OrderResponseDTO> getOrderByCode(@PathVariable String orderCode, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        OrderResponseDTO order = orderService.getOrderByCode(user, orderCode);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(@RequestParam String status, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<OrderResponseDTO> orders = orderService.getOrdersByStatus(user, status);
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/seller")
    public ResponseEntity<Page<OrderResponseDTO>> getSellerOrders(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) Boolean active,
        @RequestParam(required = false) String stock,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        Authentication auth
    ) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(orderService.findSellerOrders(search, categoryId, active, stock, pageable, user));
    }
    

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderCreateDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        
        OrderResponseDTO order = orderService.createOrder(dto, user);

        return ResponseEntity.ok(order);
    }

    

    @PostMapping("/{orderCode}/pay")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable String orderCode, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        OrderResponseDTO order = orderService.payOrder(orderCode, user);
        return ResponseEntity.ok(order);
    }

    
}
