package com.example.nexo.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.product.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    
}
