package com.example.nexo.repository.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Query("SELECT o FROM Order o WHERE o.user = :user ORDER BY o.createdAt DESC")
    List<Order> findLatestOrdersByUser(@Param("user") User user);
    @Query("""
        SELECT COALESCE(SUM(oi.priceAtPurchase * oi.quantity), 0) 
        FROM OrderItem oi 
        WHERE oi.seller.id = :sellerId 
          AND oi.order.paymentStatus = 'PAID'
    """)
    BigDecimal calculateTotalSalesBySeller(
        @Param("sellerId") Long sellerId
    ); 
}
