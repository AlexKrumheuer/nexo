package com.example.nexo.dto.seller;

import java.math.BigDecimal;

import com.example.nexo.dto.auth.UserResponseDTO;
import com.example.nexo.entity.user.SellerStatus;

public record SellerResponseDTO(
    UserResponseDTO user,
    String companyName,
    String cpf,
    String cnpj,
    String supportPhone,
    SellerStatus status,
    String slug,
    String logoUrl,
    BigDecimal comissionRate
) {
    
}
