package com.example.nexo.dto.product;
public record UpdateCategoryDto(
        String name,
        Long parentId,
        String slug,
        Boolean active,
        String description,
        String imageUrl,
        Integer displayOrder
) {}
