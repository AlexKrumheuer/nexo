package com.example.nexo.dto.user;


public record AddressResponseDTO(
    Long id,
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,    
    String state,
    String zipCode,
    String addressType
    ) {}

