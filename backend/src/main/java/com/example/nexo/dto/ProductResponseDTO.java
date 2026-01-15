package com.example.nexo.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDTO(
        Long id,
        String title,
        BigDecimal price,
        BigDecimal finalPrice,
        Integer discountPercent,
        Integer stockQuantity,
        String brand,
        String slug,
        List<String> images
) {}