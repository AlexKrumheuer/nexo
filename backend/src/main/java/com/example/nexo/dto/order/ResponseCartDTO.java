package com.example.nexo.dto.order;

import com.example.nexo.dto.product.ProductResponseDTO;

public record ResponseCartDTO (
    ProductResponseDTO product,
    int quantity
) {
    
}
