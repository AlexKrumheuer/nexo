package com.example.nexo.dto.product;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderCreateDTO(
    @NotEmpty(message="At least one order item is required")
    List<OrderItemCreateDTO> items,

    @NotNull(message="Shipping price is required")
    BigDecimal shippingPrice,

    @NotBlank(message="Payment method is required")
    String paymentMethod,
    @NotNull(message="Address ID is required")
    Long address

) { }   
