package com.example.nexo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
