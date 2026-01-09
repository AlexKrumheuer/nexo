package com.example.nexo.dto;

import java.math.BigDecimal;

public record CreateProductDTO(
    String title,
    String description,
    BigDecimal price,
    Integer discountPercent,
    String brand
) {}
