package com.example.nexo.dto.product;

import com.example.nexo.dto.seller.SellerResumedResponseDTO;

public record OrderItemResponseDTO (
    Long order,
    ProductResponseDTO product,
    SellerResumedResponseDTO seller,
    Integer quantity
) {}
