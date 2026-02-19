package com.example.nexo.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.nexo.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long>,
                                           JpaSpecificationExecutor<Product> // CLASS TO CREATE DINAMIC SQL QUERIES 
{
    Optional<Product> findBySlug(String slug);

    Optional<Product> findById(Long id);
    boolean existsBySlug(String Slug);
    @Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT 10", nativeQuery = true)
    List<Product> findRandomProducts();
    List<Product> findTop10ByOrderByIdDesc();
    Page<Product> findAll(Pageable pageable);
}