package com.example.nexo.service.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.nexo.dto.product.OrderCreateDTO;
import com.example.nexo.dto.product.OrderItemCreateDTO;
import com.example.nexo.dto.product.OrderResponseDTO;
import com.example.nexo.entity.order.OrderItem;
import com.example.nexo.entity.order.PaymentType;
import com.example.nexo.entity.product.Order;
import com.example.nexo.entity.product.OrderStatus;
import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.user.Address;
import com.example.nexo.entity.user.User;
import com.example.nexo.infra.exception.ProductException;
import com.example.nexo.repository.order.OrderItemRepository;
import com.example.nexo.repository.order.OrderRepository;
import com.example.nexo.repository.product.ProductRepository;
import com.example.nexo.repository.user.AddressRepository;
import com.example.nexo.util.Mapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final Mapper mapper;

    @Transactional
    public OrderResponseDTO createOrder(OrderCreateDTO dto, User user) {
        
        List<OrderItem> orderItems = new ArrayList<>();

        // Payment
        BigDecimal subtotal = BigDecimal.ZERO; // Sum of all original values (without discount)
        BigDecimal totalDiscount = BigDecimal.ZERO; // Sum of all the money saved
        BigDecimal totalToPay = BigDecimal.ZERO; // What the customer really will pay for the items

        for (OrderItemCreateDTO itemDto : dto.items()) {
            Product product = productRepository.findById(itemDto.product())
                    .orElseThrow(() -> new ProductException("Product Not found", HttpStatus.NOT_FOUND));

            // Convert the quantity to BigDecimal for accurate calculations
            BigDecimal quantity = new BigDecimal(itemDto.quantity());

            // Create and salve order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPriceAtPurchase(product.getFinalPrice());
            orderItems.add(orderItem);

            BigDecimal originalItemTotal = product.getPrice().multiply(quantity);

            BigDecimal finalItemTotal = product.getFinalPrice().multiply(quantity);

            BigDecimal itemDiscount = originalItemTotal.subtract(finalItemTotal);

            subtotal = subtotal.add(originalItemTotal);
            totalDiscount = totalDiscount.add(itemDiscount);
            totalToPay = totalToPay.add(finalItemTotal);
        }

        // Implementation for creating an order based on the provided DTO and user
        // information.

        Order order = new Order();
        // User that placed the order
        order.setUser(user);
        // Address
        Address address = addressRepository.findById(dto.address())
                .orElseThrow(() -> new ProductException("Address not found", HttpStatus.NOT_FOUND));
        
        order.setShippingStreet(address.getStreet());
        order.setShippingNumber(address.getNumber());
        order.setShippingComplement(address.getComplement());
        order.setShippingNeighborhood(address.getNeighborhood());
        order.setShippingCity(address.getCity());
        order.setShippingState(address.getState());
        order.setShippingZipCode(address.getZipCode());

        order.setStatus(OrderStatus.PENDING);
           
        order.setShippingPrice(dto.shippingPrice());
        order.setPaymentMethod(PaymentType.valueOf(dto.paymentMethod()));
        order.setSubtotal(subtotal);
        order.setDiscountPrice(totalDiscount);
        order.setTotalPrice(totalToPay);
        orderRepository.save(order);
        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        order.setOrderList(orderItems);
        order.setCreatedAt(LocalDateTime.now());

        return mapper.MapperOrderResponse(order);
    }
}
