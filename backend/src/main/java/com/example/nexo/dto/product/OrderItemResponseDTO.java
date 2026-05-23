package com.example.nexo.dto.product;


public record OrderItemResponseDTO (
    Long order,
    ProductResponseDTO product,
    Integer quantity
) {}
