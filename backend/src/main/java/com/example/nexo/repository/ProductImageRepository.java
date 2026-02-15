package com.example.nexo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    
}
