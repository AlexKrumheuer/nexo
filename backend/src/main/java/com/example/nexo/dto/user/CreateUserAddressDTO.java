package com.example.nexo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserAddressDTO(
    @NotBlank(message="Street must not be blank")
    @Size(min = 3, max = 255, message="Street must have between 3 and 255 chars")
    String street,
    @NotBlank(message="Number must have a max of 20 chars")
    @Size(max=20, message="Number must have a max of 20 chars")
    String number,
    @Size(max=255, message="Complement must have a max of 255 chars")
    String complement,
    @NotBlank(message="City is Obligated")
    String city,    
    @NotBlank(message="State is Obligated")
    @Size(min=2, max=2, message="Use state's abbreviation")
    String state,
    @NotBlank(message="CEP is obligated")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP must be in format 00000000 or 00000-000")
    String zipCode,
    @NotBlank(message="Address type must not be blank")
    String addressType
) {
}
