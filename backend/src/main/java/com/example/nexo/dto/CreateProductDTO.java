package com.example.nexo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank (message = "Título Obrigatório")
        @Max(150)
        String title,

        @NotBlank(message = "A descrição é obrigatória")
        @Max(1000)
        String description,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0,01", message = "O preço deve ser maior que 0")
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @Min(value = 0, message = "O desconto deve ser maior que 0")
        @Max(value = 100, message = "O desconto não pode ser maior que 100%")
        Integer discountPercent,
        BigDecimal finalPrice,
        Integer stockQuantity,
        @NotBlank(message = "A marca é obrigatória")
        @Size(min = 2, max = 80)
        String brand
) {}