package com.example.nexo.dto;

import java.math.BigDecimal;

import com.example.nexo.entity.Product;
import com.example.nexo.entity.Seller;
import com.example.nexo.entity.order.Order;

public record CreateOrderItemDTO (
    Order order,
    Product product,
    Seller seller,
    int quantity,
    BigDecimal priceAtPurchase
) {
    
}
