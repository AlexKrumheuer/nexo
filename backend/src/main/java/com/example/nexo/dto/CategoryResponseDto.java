package com.example.nexo.dto;

public record CategoryResponseDto(
        Long id,
        String name,
        String slug,
        Boolean actige,
        String description,
        String imageUrl,
        Integer displayOrder,
        Long parentId
) {
}
