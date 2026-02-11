package com.example.nexo.entity.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.nexo.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
    @OneToMany
    @JoinColumn(name = "user_id")
    User user;
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default current_timestamp")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default current_timestamp on")
    private LocalDateTime updatedAt; 

    @JsonManagedReference
    private List<OrderItem> orderList;

    public Order(BigDecimal totalPrice, OrderStatus status) {
        this.totalPrice = totalPrice;
        this.status = status;
    }
    
}
