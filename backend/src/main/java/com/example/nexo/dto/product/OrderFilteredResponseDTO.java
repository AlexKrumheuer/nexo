package com.example.nexo.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.nexo.dto.seller.SellerResponseDTO;
import com.example.nexo.dto.user.UserResponseDTO;

public record OrderFilteredResponseDTO(
    Long id,
    BigDecimal subtotal,
    BigDecimal shippingPrice,
    BigDecimal discountPrice,
    BigDecimal totalPrice,
    ProductResponseDTO product,
    SellerResponseDTO seller,
    UserResponseDTO user,
    String status,
    String paymentMethod,
    String shippingStreet,
    String shippingNumber,
    String shippingComplement,
    String shippingNeighborhood,
    String shippingCity,
    String shippingState,
    String shippingZipCode,
    String orderCode,
    String trackingCode,
    String shippingStatus,
    Integer quantity,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
}
