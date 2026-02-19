package com.example.nexo.dto.product;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(
        @NotBlank(message="Category name must not be blank")
        String name,
        Long parentID,
        @NotBlank(message="Category must have a description")
        String description,
        String imageUrl,
        Integer displayOrder
) {
}
