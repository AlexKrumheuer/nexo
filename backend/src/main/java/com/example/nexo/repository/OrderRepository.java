package com.example.nexo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
