package com.example.nexo.dto.product;

import com.example.nexo.dto.seller.SellerResumedResponseDTO;
import com.example.nexo.entity.product.DeliveryStatus;

public record OrderItemResponseDTO (
    Long order,
    ProductResponseDTO product,
    SellerResumedResponseDTO seller,
    DeliveryStatus shippingStatus,
    Integer quantity
) {}
