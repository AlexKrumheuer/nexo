package com.example.nexo.repository.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.product.Order;
import com.example.nexo.entity.product.OrderStatus;
import com.example.nexo.entity.user.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserAndOrderCode(User user, String orderCode);
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    List<Order> findByUserAndStatusOrderByCreatedAtDesc(User user, OrderStatus status);
}
