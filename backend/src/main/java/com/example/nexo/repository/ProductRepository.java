package com.example.nexo.repository;

import com.example.nexo.entity.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySlug(String slug);
    boolean existsBySlug(String Slug);
    @Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Product> findRandomProducts();
    List<Product> findTop10ByOrderByIdDesc();
}
