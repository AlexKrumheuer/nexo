package com.example.nexo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String slug;

    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

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
    private String sku; 

    private String brand;

   @Column(name = "created_at", columnDefinition = "TIMESTAMP default current_timestamp")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default current_timestamp on")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();
    
    public Product(String title, BigDecimal price, Integer discountPercent, BigDecimal finalPrice, Integer stockQuantity, String brand){
        this.title = title;
        this.price = price;
        this.discountPercent = discountPercent;
        this.finalPrice = finalPrice;
        this.stockQuantity = stockQuantity;
        this.brand = brand;
    }
}
