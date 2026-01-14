package com.example.nexo.controller;

import com.example.nexo.dto.CreateProductDTO;
import com.example.nexo.dto.ProductResponseDTO;
import com.example.nexo.dto.UpdateProductDTO;
import com.example.nexo.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid CreateProductDTO dto) {
        return ResponseEntity.ok(productService.create(dto));
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
