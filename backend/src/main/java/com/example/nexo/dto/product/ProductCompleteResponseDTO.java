package com.example.nexo.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.nexo.dto.seller.SellerResumedResponseDTO;


public record ProductCompleteResponseDTO(
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
        List<ProductImageResponseDTO> images,
        SellerResumedResponseDTO seller
) {}

