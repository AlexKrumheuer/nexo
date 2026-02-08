package com.example.nexo.dto;

public record CreateCategoryDto(
        String name,
        String slug,
        Long parentId,
        Boolean active,
        String description,
        String imageUrl,
        Integer displayOrder
) {}