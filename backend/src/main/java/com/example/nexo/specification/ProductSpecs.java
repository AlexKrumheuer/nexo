package com.example.nexo.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.nexo.entity.product.Product;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecs {

    // 1. FILTER BY PRODUCT TITLE
    public static Specification<Product> hasNameLike(String name) {
        return (root, query, builder) -> 
            builder.like(builder.lower(root.get("title")), "%" + name.toLowerCase() + "%");
    }

    // 2. FILTER BY CATEGORY
    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, builder) -> 
            builder.equal(root.get("category").get("id"), categoryId);
    }

    // 3. FILTER BY PRODUCT STATUS (ACTIVE, INACTIVE)
    public static Specification<Product> isActive(Boolean status) {
        return (root, query, builder) -> 
            builder.equal(root.get("active"), status);
    }

    // 4. STOCK LOGIC
    public static Specification<Product> hasStockStatus(String stockStatus) {
        return (root, query, builder) -> {
            if ("OUT_OF_STOCK".equals(stockStatus)) {
                // STOCK EQUALS 0
                return builder.equal(root.get("stockQuantity"), 0);
            } 
            else if ("LOW_STOCK".equals(stockStatus)) {
                // STOCK BETWEEN 1 AND 5
                Predicate quantityLow = builder.between(root.get("stockQuantity"), 1, 5);
                Predicate isNotLuxury = builder.lessThan(root.get("price"), 50000.00); // 50k
                return builder.and(quantityLow, isNotLuxury);
            } 
            else if ("IN_STOCK".equals(stockStatus)) {
                // STOCK GREATER THAN 3
                return builder.greaterThan(root.get("stockQuantity"), 0);
            }
            return null;
        };
    }
}