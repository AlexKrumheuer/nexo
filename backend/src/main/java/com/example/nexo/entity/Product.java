package com.example.nexo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
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

    @Column(nullable = false)
    private Integer discountPercent;

    @Column(nullable = false)
    private BigDecimal finalPrice;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(unique = true)
    private String sku; //código produto

    private String brand;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Product(String title, BigDecimal price, Integer discountPercent, BigDecimal finalPrice, Integer stockQuantity, String brand){
        this.title = title;
        this.price = price;
        this.discountPercent = discountPercent;
        this.finalPrice = finalPrice;
        this.stockQuantity = stockQuantity;
        this.brand = brand;
    }
}
