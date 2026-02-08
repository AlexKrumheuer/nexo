package com.example.nexo.controller;

import com.example.nexo.dto.CreateProductDTO;
import com.example.nexo.dto.ProductResponseDTO;
import com.example.nexo.dto.UpdateProductDTO;
import com.example.nexo.service.ProductService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@EnableMethodSecurity(securedEnabled = true)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid CreateProductDTO dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductResponseDTO> findBySlug(@PathVariable String slug) {
        ProductResponseDTO product = productService.productSlug(slug);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @GetMapping("/random")
    public ResponseEntity<List<ProductResponseDTO>> getRandomProducts() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @GetMapping("/last")
    public ResponseEntity<List<ProductResponseDTO>> getLastProducts() {
        return ResponseEntity.ok(productService.lastProducts());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductDTO dto
            ) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
