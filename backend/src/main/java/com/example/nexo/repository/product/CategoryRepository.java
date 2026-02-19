package com.example.nexo.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nexo.entity.product.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug (String slug);

    Page<Category> findAll(Pageable pageable);

    boolean existsBySlug (String slug);
    boolean existsByName (String name);
    boolean existsByParentId (Long id);

    List<Category> findByActiveTrueOrderByDisplayOrderAsc();
}
