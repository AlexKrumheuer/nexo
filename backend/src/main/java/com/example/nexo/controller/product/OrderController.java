package com.example.nexo.controller.product;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    


    @PostMapping
    public String createOrder() {
        return "Order created successfully!";
    }
}
