package com.example.nexo.dto.order;

import java.math.BigDecimal;

import com.example.nexo.entity.order.Order;
import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.user.Seller;

public record CreateOrderItemDTO (
    Order order,
    Product product,
    Seller seller,
    int quantity,
    BigDecimal priceAtPurchase
) {
    
}
