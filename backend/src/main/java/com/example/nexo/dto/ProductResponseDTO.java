package com.example.nexo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDTO(
        Long id,
        String title,
        BigDecimal price,
        BigDecimal finalPrice,
        String description,
        Integer discountPercent,
        Integer stockQuantity,
        String brand,
        Boolean active,
        CategoryResponseDTO category,
        String slug,
        String sku,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ProductImageResponseDTO> images
) {}

