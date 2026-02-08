package com.example.nexo.dto;

public record UpdateCategoryDto(
        String name,
        String slug,
        Long parentId,
        Boolean active,
        String description,
        String imageUrl,
        Integer displayOrder
) {}
