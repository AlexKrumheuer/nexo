package com.example.nexo.dto.product;


public record CategoryResponseDTO( 
    Long id,
    String name,
    String slug,
    String description,
    Boolean active,
    String imageUrl,
    Integer displayOrder,
    Long parentId
    )
    {
}
