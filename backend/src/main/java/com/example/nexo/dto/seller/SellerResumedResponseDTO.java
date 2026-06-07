package com.example.nexo.dto.seller;



public record SellerResumedResponseDTO(
    String companyName,
    String cpf,
    String cnpj,
    String supportPhone,
    String logoUrl
) {
    
}
