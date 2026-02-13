package com.example.nexo.dto;


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
