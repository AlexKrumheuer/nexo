package com.example.nexo.dto.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateCartItemDTO( 
    @NotNull(message="Product Id must not be null")
    Long productId,
    @Min(value = 1, message="User must add at least one item")
    @Max(value = 100000, message="User must not buy more than a 1000")
    int quantity
    ) {
} 
