package com.example.nexo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank (message = "Título Obrigatório")
        String title,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @Min(value = 0, message = "O desconto deve ser maior que 0")
        @Max(value = 100, message = "O desconto não pode ser maior que 100%")
        Integer discountPercent,

        BigDecimal finalPrice,

        Integer stockQuantity,
        @NotNull(message="Category must not be blank")
        Long categoryId,

        @NotBlank(message = "A marca é obrigatória")
        @Size(min = 2, max = 80)
        String brand,
        @NotNull(message="Status of product must not be null")
        boolean active,
        @NotBlank(message="SKU code must not be blank")
        String sku
) {}