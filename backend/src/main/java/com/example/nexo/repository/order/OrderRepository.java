package com.example.nexo.repository.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nexo.entity.product.DeliveryStatus;
import com.example.nexo.entity.product.Order;
import com.example.nexo.entity.product.PaymentStatus;
import com.example.nexo.entity.user.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserAndOrderCode(User user, String orderCode);

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    List<Order> findByUserAndPaymentStatusOrderByCreatedAtDesc(User user, PaymentStatus status);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderList i WHERE o.user = :user AND i.shippingStatus IN :statuses")
    List<Order> findOrdersByDeliveryStatus(
            @Param("user") User user,
            @Param("statuses") List<DeliveryStatus> statuses);

    List<Order> findByOrderList_SellerOrderByCreatedAtDesc(User user);
}
