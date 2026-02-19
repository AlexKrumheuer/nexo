package com.example.nexo.repository.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.order.Cart;
import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.user.User;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndProduct(User user, Product product);
    List<Cart> findAllByUser(User user);
    void deleteAllByUser(User user);
    Boolean existsByUser(User user);
}
