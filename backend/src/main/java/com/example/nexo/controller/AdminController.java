package com.example.nexo.controller;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@EnableMethodSecurity(securedEnabled = true)
public class AdminController {
    
}
