package com.example.nexo.repository.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.nexo.entity.order.OrderItem;
import com.example.nexo.entity.product.DeliveryStatus;
import com.example.nexo.entity.product.PaymentStatus;
import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.User;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>,  
                                                    JpaSpecificationExecutor<OrderItem> {
    Page<OrderItem> findAll(Pageable pageable);
    Integer countBySellerAndShippingStatusIn(Seller seller, List<DeliveryStatus> shippingStatuses);
    List<OrderItem> findBySeller(Seller seller);

}
