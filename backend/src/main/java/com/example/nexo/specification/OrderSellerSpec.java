package com.example.nexo.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.nexo.entity.order.OrderItem;

import jakarta.persistence.criteria.Predicate;

public class OrderSellerSpec {

    // 1. FILTER BY PRODUCT TITLE
    public static Specification<OrderItem> hasNameLike(String name) {
        return (root, query, builder) -> 
            builder.like(builder.lower(root.get("product").get("title")), "%" + name.toLowerCase() + "%");
    }

    // 2. FILTER BY CATEGORY
    public static Specification<OrderItem> hasCategory(Long categoryId) {
        return (root, query, builder) -> 
            builder.equal(root.get("product").get("category").get("id"), categoryId);
    }

    // 3. FILTER BY PRODUCT STATUS (ACTIVE, INACTIVE)
    public static Specification<OrderItem> isActive(Boolean status) {
        return (root, query, builder) -> 
            builder.equal(root.get("product").get("active"), status);
    }

    // 4. STOCK LOGIC
    public static Specification<OrderItem> hasStockStatus(String stockStatus) {
        return (root, query, builder) -> {
            if ("OUT_OF_STOCK".equals(stockStatus)) {
                // STOCK EQUALS 0
                return builder.equal(root.get("stockQuantity"), 0);
            } 
            else if ("LOW_STOCK".equals(stockStatus)) {
                // STOCK BETWEEN 1 AND 5
                Predicate quantityLow = builder.between(root.get("stockQuantity"), 1, 5);
                Predicate isNotLuxury = builder.lessThan(root.get("product").get("price"), 50000.00); // 50k
                return builder.and(quantityLow, isNotLuxury);
            } 
            else if ("IN_STOCK".equals(stockStatus)) {
                // STOCK GREATER THAN 3
                return builder.greaterThan(root.get("stockQuantity"), 0);
            }
            return null;
        };
    }

    // 5. FILTER BY SELLER 
    public static Specification<OrderItem> belongsToSeller(Long sellerId) {
        return (root, query, builder) ->
            builder.equal(root.get("product").get("seller").get("id"), sellerId);
    }
}