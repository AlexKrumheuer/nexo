package com.example.nexo.dto.product;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemCreateDTO (
    @NotNull(message="Product ID is required")
    Long product,
    @Min(value=1, message="Quantity must be at least 1")
    Integer quantity
) {}
