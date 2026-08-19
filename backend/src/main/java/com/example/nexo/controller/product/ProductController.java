package com.example.nexo.controller.product;

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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.nexo.dto.product.CreateProductDTO;
import com.example.nexo.dto.product.ProductResponseDTO;
import com.example.nexo.dto.product.UpdateProductDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.service.product.ProductService;

import jakarta.validation.Valid;


// Endpoints related to products


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
            @Valid @ModelAttribute CreateProductDTO dto,
            @RequestPart(value="images", required=true) List<MultipartFile> images,
            Authentication auth
        ) {
            User user = (User) auth.getPrincipal();

            ProductResponseDTO createdProduct = productService.createProductWithImages(dto, images, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);    
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/seller")
    public ResponseEntity<Page<ProductResponseDTO>> getSellerProducts(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) Boolean active,
        @RequestParam(required = false) String stock,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        Authentication auth
    ) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(productService.findSellerProducts(search, categoryId, active, stock, pageable, user));
    }

    @GetMapping("/public/slug/{slug}")
    public ResponseEntity<ProductResponseDTO> findBySlug(@PathVariable String slug) {
        ProductResponseDTO product = productService.productSlug(slug);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/public")
    public ResponseEntity<Page<ProductResponseDTO>> getProducts(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) Boolean active,
        @RequestParam(required = false) String stock,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
) {
        return ResponseEntity.ok(productService.findProducts(search, categoryId, active, stock, pageable));
    }

    @GetMapping("/public/random")
    public ResponseEntity<List<ProductResponseDTO>> getRandomProducts() {
        return ResponseEntity.ok(productService.randomProducts());
    }

    @GetMapping("/public/last")
    public ResponseEntity<List<ProductResponseDTO>> getLastProducts() {
        return ResponseEntity.ok(productService.lastProducts());
    }
    

    @GetMapping("/public/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductDTO dto,
            Authentication auth
            ) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(productService.update(id, dto, user));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id,
        Authentication auth
    ) {
        User user = (User) auth.getPrincipal();
        productService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
