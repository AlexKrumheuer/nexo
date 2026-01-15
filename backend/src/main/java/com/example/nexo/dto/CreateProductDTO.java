package com.example.nexo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

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

        @NotBlank(message = "A marca é obrigatória")
        @Size(min = 2, max = 80)
        String brand,
        @NotEmpty(message = "The product must have at least one image")
        @Size(max = 5, message = "Max o 5 images by product")
        List<String> images
) {}