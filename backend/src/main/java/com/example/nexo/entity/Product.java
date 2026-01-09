package com.example.nexo.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true)
    private String slug;  //url amigavel

    @Column(columnDefinition = "TEXT")
    private String description;

    private String shortDescription;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer discountPercent;

    private BigDecimal finalPrice;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(unique = true)
    private String sku; //código produto

    private String brand;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}