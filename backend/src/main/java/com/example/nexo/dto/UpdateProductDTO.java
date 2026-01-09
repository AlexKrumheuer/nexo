package com.example.nexo.dto;

import java.math.BigDecimal;

public record UpdateProductDTO(
        String title,
        String description,
        BigDecimal price,
        Integer discountPercent,
        Integer stockQuantity,
        String brand
) {}

