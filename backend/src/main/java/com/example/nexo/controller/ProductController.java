package com.example.nexo.controller;

import com.example.nexo.dto.CreateProductDTO;
import com.example.nexo.dto.ProductResponseDTO;
import com.example.nexo.dto.UpdateProductDTO;
import com.example.nexo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/products")
@EnableMethodSecurity(securedEnabled = true)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> create(
            @RequestPart("data") String productDataJson,
            @RequestPart(value="images", required=false) List<MultipartFile> images
        ) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            CreateProductDTO dto = mapper.readValue(productDataJson, CreateProductDTO.class);

            ProductResponseDTO createdProduct = productService.createProductWithImages(dto, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);    
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductResponseDTO> findBySlug(@PathVariable String slug) {
        ProductResponseDTO product = productService.productSlug(slug);
        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponseDTO>> getProducts(
        @PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(productService.findProducts(pageable));
    }

    @GetMapping("/random")
    public ResponseEntity<List<ProductResponseDTO>> getRandomProducts() {
        return ResponseEntity.ok(productService.randomProducts());
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
