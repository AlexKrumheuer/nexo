package com.example.nexo.dto.order;

import com.example.nexo.entity.product.Product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ResponseCartDTO (
    @NotNull(message="Product Id must not be null")
    Product product,
    @Min(value = 1, message="User must add at least one item")
    @Max(value = 100000, message="User must not buy more than a 1000")
    int quantity
) {
    
}
