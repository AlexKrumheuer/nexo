package com.example.nexo.repository;

import com.example.nexo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySlug(String Slug);
}
