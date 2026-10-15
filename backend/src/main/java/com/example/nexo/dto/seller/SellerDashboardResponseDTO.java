package com.example.nexo.dto.seller;

import java.math.BigDecimal;
import java.util.List;

import com.example.nexo.dto.product.OrderFilteredResponseDTO;
import com.example.nexo.dto.product.OrderResponseDTO;

public record SellerDashboardResponseDTO(
    BigDecimal totalSales,
    Integer pendingOrders,
    Integer totalProducts,
    Integer lowStock,
    List<OrderFilteredResponseDTO> latestOrders
){
    
}
