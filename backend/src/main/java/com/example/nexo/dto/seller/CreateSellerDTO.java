package com.example.nexo.dto.seller;

import jakarta.validation.constraints.NotBlank;

public record CreateSellerDTO(
    @NotBlank(message = "Company must not be blank")
    String storeName,
    @NotBlank(message = "You must select how you will going to work")
    String type,
    String cpf,
    String cnpj,
    String supportPhone
) {
    
}
