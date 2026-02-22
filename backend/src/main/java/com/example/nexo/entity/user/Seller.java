package com.example.nexo.entity.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId 
    @JoinColumn(name = "id") 
    private User user;

    @NotBlank(message = "Store name is required")
    @Size(min = 2, max = 255, message = "Store name must be between 2 and 255 characters")
    @Column(nullable = false)
    private String storeName;

    @NotBlank(message = "Slug is required")
    @Column(unique = true, nullable = false)
    private String slug;

    @NotBlank(message = "CNPJ is required")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "Invalid CNPJ format. Use XX.XXX.XXX/0001-XX")
    @Column(unique = true)
    private String cnpj;

    @Size(max = 20)
    private String supportPhone;

    @URL(message = "Logo URL must be valid")
    private String logoUrl;

    @NotNull(message = "Commission rate is required")
    @DecimalMin(value = "0.00", message = "Commission cannot be negative")
    @DecimalMax(value = "100.00", message = "Commission cannot be greater than 100%")
    private BigDecimal commissionRate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SellerStatus status = SellerStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}