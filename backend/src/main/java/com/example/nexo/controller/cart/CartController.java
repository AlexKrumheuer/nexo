package com.example.nexo.controller.cart;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nexo.dto.CreateCartItemDTO;
import com.example.nexo.dto.ResponseCartDTO;
import com.example.nexo.dto.UpdateCartDTO;
import com.example.nexo.entity.User;
import com.example.nexo.service.CartService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseCartDTO>> listCart(Authentication auth) {
        User user = (User) auth.getPrincipal();
        List<ResponseCartDTO> cartItems = cartService.findAllCartItems(user);
        return ResponseEntity.ok(cartItems);
    }
    

    @PostMapping("")
    public ResponseEntity<ResponseCartDTO> addToCart(@RequestBody @Valid CreateCartItemDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        ResponseCartDTO cart = cartService.addToCart(dto, user); 
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<ResponseCartDTO> editCartItem(@PathVariable("product_id") Long productId, @RequestBody @Valid UpdateCartDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        ResponseCartDTO updatedCart = cartService.editCartItem(dto, user, productId);
        
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> removeSpecificCartItem(@PathVariable("product_id") Long productId, Authentication auth){
        User user = (User) auth.getPrincipal();
        cartService.removeSpecificCartItem(productId, user);
        return ResponseEntity.noContent().build(); 
    }

    @DeleteMapping("")
    public ResponseEntity<Void> cleanCart(Authentication auth){
        User user = (User) auth.getPrincipal();
        cartService.cleanCart(user);
        return ResponseEntity.noContent().build(); 
    }
}
