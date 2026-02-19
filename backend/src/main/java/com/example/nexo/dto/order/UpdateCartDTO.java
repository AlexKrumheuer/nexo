package com.example.nexo.dto.order;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateCartDTO(
    @Min(value = 1, message="You must add at least one of this item")
    @Max(value = 100000, message="You must not add more than 100000 of this item")
    int quantity
) {
    
}
