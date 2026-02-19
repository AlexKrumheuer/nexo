package com.example.nexo.entity.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="addresses")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) 
    @NotNull(message = "User is Obligated") 
    @JsonIgnore
    private User user;

    @NotBlank(message = "Street is Obligated")
    @Size(min = 3, max = 255, message = "Street must have between 3 and 255 chars")
    private String street;

    @NotBlank(message = "Number is Obligated")
    @Size(max = 20, message = "Number must have a max of 20 chars")
    private String number;

    @Size(max = 255, message = "Complement must have a max of 255 chars")
    private String complement;

    @NotBlank(message = "City is Obligated")
    private String city;

    @NotBlank(message = "State is Obligated")
    @Size(min = 2, max = 2, message = "Use state's abbreviation")
    private String state;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP must be in format 00000000 or 00000-000")
    private String zipCode;

    @NotBlank(message = "Address type is Obligated")
    private String addressType;

    @CreationTimestamp 
    @Column(updatable = false) 
    private LocalDateTime createdAt;
}