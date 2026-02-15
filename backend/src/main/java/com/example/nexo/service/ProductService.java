package com.example.nexo.service;

import com.example.nexo.dto.CategoryResponseDTO;
import com.example.nexo.dto.CreateProductDTO;
import com.example.nexo.dto.ProductImageResponseDTO;
import com.example.nexo.dto.ProductResponseDTO;
import com.example.nexo.dto.UpdateProductDTO;
import com.example.nexo.entity.Category;
import com.example.nexo.entity.Product;
import com.example.nexo.entity.ProductImage;
import com.example.nexo.infra.exception.ProductException;
import com.example.nexo.repository.CategoryRepository;
import com.example.nexo.repository.ProductImageRepository;
import com.example.nexo.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ImageService imageService;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductImageRepository productImageRepository, ImageService imageService) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    public ProductResponseDTO productSlug(String slug) {
        Product product = productRepository.findBySlug(slug)
            .orElseThrow(() -> new ProductException("Product not found by slug", HttpStatus.NOT_FOUND));

        return mapToResponse(product);  
    }


    // RESPONSIBLE FOR PRODUCT POS
    // IT SHOULD RECEIVE A DTO AND THE IMAGES OF THE PRODUCT
    @Transactional
    public ProductResponseDTO createProductWithImages (CreateProductDTO dto, List<MultipartFile> files){
        // FIRST GENERATE THE SLUG
        String slug = generateSlug(dto.title());

        Category category = null;


        // IT VERIFIES IF THE CATEGORY SENT EXISTS IN DATABASE
        if(dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ProductException("Category not found", HttpStatus.NOT_FOUND));
        }

        // IT CALCULATES THE FINAL PRICE (PRICE AFTER DISCOUNTS)
        BigDecimal finalPrice = calculateFinalPrice(dto.price(), dto.discountPercent());

        Product product = new Product(
            dto.title(), 
            slug, 
            dto.active(), 
            category,
            dto.description(),
            dto.price(),
            dto.discountPercent(),
            finalPrice,
            dto.stockQuantity(),
            dto.sku(),
            dto.brand()
        );

        // SAVE THE PRODUCT FIRST, BECAUSE ITS ID IS NEEDED TO CREATE A RELATIONSHIP WITH PRODUCT IMAGES
        // DUE TO FOREIGN KEY
        Product savedProduct = productRepository.save(product);


        // IT VERIFIES IF WAS ACTUALLY SENT A IMAGE
        if (files != null && !files.isEmpty()) {
            List<ProductImage> images = new ArrayList<ProductImage>();

            // FOREACH FILE SENT, THE FILE IS SAVED IN CLOUDINARY DATABASE
            // THEN CLOUDINARY RETURNS A UNIQUE IDENTIFIER FOR EACH IMAGE
            // THAT IT WILL BE SAVED IN PRODUCT
            for(MultipartFile file : files) {
                String imageUrl = imageService.uploadImage(file);
                ProductImage image = ProductImage.builder()
                            .url(imageUrl)
                            .product(savedProduct)
                            .build();
                images.add(image);
            }
            productImageRepository.saveAll(images);
            savedProduct.setImages(images);
        }

        return mapToResponse(savedProduct);
    }


    public List<ProductResponseDTO> randomProducts() {
        return productRepository.findRandomProducts() 
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    public List<ProductResponseDTO> lastProducts() {
        return productRepository.findTop10ByOrderByIdDesc() 
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findProducts(Pageable pageable) {
        return productRepository.findAll(pageable) 
            .map(this::mapToResponse);
    }





    public ProductResponseDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        return mapToResponse(product);
    }





    
    public ProductResponseDTO update(Long id, UpdateProductDTO dto){

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        if (dto.title() != null) product.setTitle(dto.title());
        if (dto.description() != null) product.setDescription(dto.description());
        if (dto.price() != null) product.setPrice(dto.price());
        if (dto.discountPercent() != null) product.setDiscountPercent(dto.discountPercent());
        if (dto.stockQuantity() != null) product.setStockQuantity(dto.stockQuantity());
        if (dto.brand() != null) product.setBrand(dto.brand());
        if(dto.active() != null) product.setActive(dto.active());


        if (dto.categoryId() != null) {
             Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new ProductException("Category not found", HttpStatus.NOT_FOUND));
             product.setCategory(category);
        }

        product.setFinalPrice(calculateFinalPrice(product.getPrice(), product.getDiscountPercent()));
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);

        return mapToResponse(product);
    }



    public void delete(Long id) {
        if (!productRepository.existsById(id))
            throw new ProductException("Product not found", HttpStatus.NOT_FOUND);

        productRepository.deleteById(id);
    }


    // THIS METHOD IS RESPONSIBLE FOR CREATING A RESPONSE PRODUCT
    // TO FRONTEND
    private ProductResponseDTO mapToResponse(Product product) {
        List<ProductImageResponseDTO> imageDtos = product.getImages() != null
        ? product.getImages().stream()
            .map(img -> new ProductImageResponseDTO(img.getId(), img.getUrl()))
            .toList()
        : Collections.emptyList();

        CategoryResponseDTO categoryDto = null;
        if (product.getCategory() != null) {
            categoryDto = new CategoryResponseDTO(
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getSlug(),
                product.getCategory().getDescription(),
                product.getCategory().getActive(),
                product.getCategory().getImageUrl(),
                product.getCategory().getDisplayOrder(),
                product.getCategory().getParent() != null ? product.getCategory().getParent().getId() : null
            );
        }
            
        return new ProductResponseDTO(
            product.getId(),
            product.getTitle(),
            product.getPrice(),
            product.getFinalPrice(),
            product.getDescription(),
            product.getDiscountPercent(),
            product.getStockQuantity(),
            product.getBrand(),
            product.getActive(),
            categoryDto,
            product.getSlug(),
            product.getSku(),
            product.getCreatedAt(),
            product.getUpdatedAt(),
            imageDtos
        );
}

    private BigDecimal calculateFinalPrice(BigDecimal price, Integer discount){
        if (discount == null || discount ==0)
            return price;
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount))
                        .divide(BigDecimal.valueOf(100))
        );
    }

    private String generateSlug(String title) {
        String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        slug = pattern.matcher(slug).replaceAll("");


        slug = slug.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") 
                .replaceAll("\\s+", "-"); 

        String originalSlug = slug;
        int count = 1;
        while (productRepository.existsBySlug(slug)) {
            slug = originalSlug + "-" + count;
            count++;
        }
        
        return slug;
    }
}