package com.example.nexo.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.product.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
