package com.example.nexo.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record OrderResponseDTO(
    List<OrderItemResponseDTO> items,
    BigDecimal subtotal,
    BigDecimal shippingPrice,
    BigDecimal discountPrice,
    BigDecimal totalPrice,

    String status,
    String paymentMethod,
    String shippingStreet,
    String shippingNumber,
    String shippingComplement,
    String shippingNeighborhood,
    String shippingCity,
    String shippingState,
    String shippingZipCode,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) { }
