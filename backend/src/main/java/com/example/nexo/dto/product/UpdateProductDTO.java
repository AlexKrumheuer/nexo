package com.example.nexo.dto.product;

import java.math.BigDecimal;

public record UpdateProductDTO(
        String title,
        String description,
        BigDecimal price,
        Integer discountPercent,
        Integer stockQuantity,
        Boolean active,
        Long categoryId,
        String brand
) {
     
}

