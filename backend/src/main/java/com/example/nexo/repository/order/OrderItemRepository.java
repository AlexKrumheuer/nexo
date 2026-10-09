package com.example.nexo.repository.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.nexo.entity.order.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>,  
                                                    JpaSpecificationExecutor<OrderItem> {
    Page<OrderItem> findAll(Pageable pageable);
}
