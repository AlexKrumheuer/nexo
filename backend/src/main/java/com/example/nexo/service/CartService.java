package com.example.nexo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.nexo.dto.CreateCartItemDTO;
import com.example.nexo.dto.ResponseCartDTO;
import com.example.nexo.dto.UpdateCartDTO;
import com.example.nexo.entity.Cart;
import com.example.nexo.entity.Product;
import com.example.nexo.entity.User;
import com.example.nexo.infra.exception.CartException;
import com.example.nexo.infra.exception.ProductException;
import com.example.nexo.repository.CartRepository;
import com.example.nexo.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public ResponseCartDTO addToCart(CreateCartItemDTO dto, User user) {

        Product product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        cartRepository.findByUserAndProduct(user, product)
            .ifPresent(c -> {
                throw new CartException("This product is already in your cart", HttpStatus.CONFLICT);
        });

        Cart newCartItem = new Cart();
        newCartItem.setUser(user);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(dto.quantity());

        cartRepository.save(newCartItem);
        return new ResponseCartDTO(newCartItem.getProduct(), newCartItem.getQuantity());
    }

    public List<ResponseCartDTO> findAllCartItems(User user){
        List<Cart> cartItems = cartRepository.findAllByUser(user);

        return cartItems.stream()
            .map(item -> new ResponseCartDTO(item.getProduct(), item.getQuantity()))
            .toList();
    }
    @Transactional
    public ResponseCartDTO editCartItem(UpdateCartDTO dto, User user, Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        Cart cartToBeUpdated = cartRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new CartException("Cart item not found in this user cart", HttpStatus.NOT_FOUND));
        
        cartToBeUpdated.setQuantity(dto.quantity());

        cartRepository.save(cartToBeUpdated);

        return new ResponseCartDTO(product, cartToBeUpdated.getQuantity());
    }
    
    @Transactional
    public void removeSpecificCartItem(Long productId, User user) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        Cart cartItemToBeDeleted = cartRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new CartException("Cart item not found in this user cart", HttpStatus.NOT_FOUND));
        
        cartRepository.delete(cartItemToBeDeleted);
    }

    @Transactional
    public void cleanCart(User user) {
        if(cartRepository.existsByUser(user)) {
            cartRepository.deleteAllByUser(user);
        } else {
            throw new CartException("Your cart is empty", HttpStatus.BAD_REQUEST);
        }
    }
} 
