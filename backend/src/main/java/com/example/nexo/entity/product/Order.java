package com.example.nexo.entity.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import com.example.nexo.entity.order.OrderItem;
import com.example.nexo.entity.order.PaymentType;
import com.example.nexo.entity.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@NoArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String orderCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Address
    @Column(nullable = false)
    private String shippingStreet;
    @Column(nullable = false)
    private String shippingNumber;
    private String shippingComplement;
    @Column(nullable = false)
    private String shippingNeighborhood;
    @Column(nullable = false)
    private String shippingCity;
    @Column(nullable = false)
    private String shippingState;
    @Column(nullable = false)
    private String shippingZipCode;

    // Payment
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    private BigDecimal subtotal;
    @Column(nullable = false)
    private BigDecimal shippingPrice;
    @Column(nullable = false)
    private BigDecimal discountPrice;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentMethod;
    
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default current_timestamp")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt; 

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderList;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.orderCode =generateOrderCode();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    private String generateOrderCode() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        
        String randomPart = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        
        return "NX-" + datePart + "-" + randomPart;
    }
}
