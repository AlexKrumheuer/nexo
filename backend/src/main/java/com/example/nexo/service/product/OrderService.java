package com.example.nexo.service.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.nexo.dto.product.OrderCreateDTO;
import com.example.nexo.dto.product.OrderItemCreateDTO;
import com.example.nexo.dto.product.OrderResponseDTO;
import com.example.nexo.dto.product.ProductResponseDTO;
import com.example.nexo.entity.order.OrderItem;
import com.example.nexo.entity.order.PaymentType;
import com.example.nexo.entity.product.DeliveryStatus;
import com.example.nexo.entity.product.Order;
import com.example.nexo.entity.product.PaymentStatus;
import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.user.Address;
import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.User;
import com.example.nexo.infra.exception.ProductException;
import com.example.nexo.repository.order.OrderItemRepository;
import com.example.nexo.repository.order.OrderRepository;
import com.example.nexo.repository.product.ProductRepository;
import com.example.nexo.repository.user.AddressRepository;
import com.example.nexo.repository.user.SellerRepository;
import com.example.nexo.specification.OrderSellerSpec;
import com.example.nexo.specification.ProductSpecs;
import com.example.nexo.util.Mapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final Mapper mapper;


    @Transactional
    public List<OrderResponseDTO> getOrders(User user) {
        List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);
        return orders.stream()
                .map(mapper::MapperOrderResponse)
                .toList();
    }

    @Transactional
    public OrderResponseDTO getOrderByCode(User user, String orderCode) {
        Order order = orderRepository.findByUserAndOrderCode(user, orderCode)
                .orElseThrow(() -> new ProductException("Order not found", HttpStatus.NOT_FOUND));
        return mapper.MapperOrderResponse(order);
    }   

    @Transactional
    public List<OrderResponseDTO> getOrdersByStatus(User user, String status) {
        List<Order> orders;
        if (status.equalsIgnoreCase("all")) {
            orders = orderRepository.findByUserOrderByCreatedAtDesc(user);
        } else {
            PaymentStatus orderStatus;
            try {
                orderStatus = PaymentStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ProductException("Invalid order status: " + status, HttpStatus.BAD_REQUEST);
            }
            orders = orderRepository.findByUserAndPaymentStatusOrderByCreatedAtDesc(user, orderStatus);
        }
        return orders.stream()
                .map(mapper::MapperOrderResponse)
                .toList();
    }



    @Transactional()
    public Page<OrderResponseDTO> findSellerOrders(
            String search,
            Long categoryId,
            Boolean active,
            String stockStatus,
            Pageable pageable,
            User user) {
        Seller seller = sellerRepository.findSellerByUser(user)
                .orElseThrow(() -> new ProductException("This User is not a Seller", HttpStatus.NOT_FOUND));

        Specification<OrderItem> spec = Specification.where(OrderSellerSpec.belongsToSeller(seller.getId()));
        // IF FILTERS ARE NOT NULL, THEY ARE ADDED
        if (search != null && !search.isEmpty()) {
            spec = spec.and(OrderSellerSpec.hasNameLike(search));
        }

        if (categoryId != null) {
            spec = spec.and(OrderSellerSpec.hasCategory(categoryId));
        }

        if (active != null) {
            spec = spec.and(OrderSellerSpec.isActive(active));
        }

        if (stockStatus != null && !stockStatus.isEmpty()) {
            spec = spec.and(OrderSellerSpec.hasStockStatus(stockStatus));
        }

        Page<OrderItem> page = orderItemRepository.findAll(spec, pageable);

        return page.map(mapper::MapperOrderResponse);
    }

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
            
            Seller seller = sellerRepository.findById(product.getSeller().getId())
                    .orElseThrow(() -> new ProductException("Seller Not found", HttpStatus.NOT_FOUND));

            // Convert the quantity to BigDecimal for accurate calculations
            BigDecimal quantity = new BigDecimal(itemDto.quantity());

            // Create and salve order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setSeller(seller);
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

        order.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
           
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

    // Define the Order as PAID and change each item of the order to
    @Transactional
    public OrderResponseDTO payOrder(String orderCode, User user) {
        Order order = orderRepository.findByUserAndOrderCode(user, orderCode)
                .orElseThrow(() -> new ProductException("Order not found", HttpStatus.NOT_FOUND));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ProductException("You are not authorized to pay for this order", HttpStatus.FORBIDDEN);
        }

        if (order.getPaymentStatus() != PaymentStatus.AWAITING_PAYMENT) {
            throw new ProductException("Order is not awaiting payment", HttpStatus.BAD_REQUEST);
        }

        for(OrderItem orderItem : order.getOrderList()) {
            Product product = orderItem.getProduct();
            if (product.getStockQuantity() < orderItem.getQuantity()) {
                throw new ProductException("Not enough stock for product: " + product.getTitle(), HttpStatus.BAD_REQUEST);
            }
            product.setStockQuantity(product.getStockQuantity() - orderItem.getQuantity());
            orderItem.setShippingStatus(DeliveryStatus.PENDING_SELLER);
            productRepository.save(product);
        }

        // Simulate payment processing
        order.setPaymentStatus(PaymentStatus.PAID);
        orderRepository.save(order);

        return mapper.MapperOrderResponse(order);
    }
}
