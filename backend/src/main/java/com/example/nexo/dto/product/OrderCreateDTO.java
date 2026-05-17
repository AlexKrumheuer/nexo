package com.example.nexo.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderCreateDTO {
    @NotNull(message="Address ID is required")
    Long addressId;
    @NotNull(message="Total price is required")
    BigDecimal totalPrice;
    @NotBlank(message="Payment method is required")
    String paymentMethod;
    
}
