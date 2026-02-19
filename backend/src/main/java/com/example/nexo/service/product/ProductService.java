package com.example.nexo.service.product;

import com.example.nexo.dto.product.CategoryResponseDTO;
import com.example.nexo.dto.product.CreateProductDTO;
import com.example.nexo.dto.product.ProductImageResponseDTO;
import com.example.nexo.dto.product.ProductResponseDTO;
import com.example.nexo.dto.product.UpdateProductDTO;
import com.example.nexo.entity.product.Category;
import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.product.ProductImage;
import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.User;
import com.example.nexo.infra.exception.ProductException;
import com.example.nexo.repository.product.CategoryRepository;
import com.example.nexo.repository.product.ProductImageRepository;
import com.example.nexo.repository.product.ProductRepository;
import com.example.nexo.repository.user.SellerRepository;
import com.example.nexo.service.ImageService;
import com.example.nexo.specification.ProductSpecs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ImageService imageService;
    private final SellerRepository sellerRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
            ProductImageRepository productImageRepository, ImageService imageService, SellerRepository sellerRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
        this.sellerRepository = sellerRepository;
    }

    public ProductResponseDTO productSlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ProductException("Product not found by slug", HttpStatus.NOT_FOUND));

        return mapToResponse(product);
    }

    // RESPONSIBLE FOR PRODUCT POST
    // IT SHOULD RECEIVE A DTO AND THE IMAGES OF THE PRODUCT
    @Transactional
    public ProductResponseDTO createProductWithImages(CreateProductDTO dto, List<MultipartFile> files, User user) {
        // FIRST GENERATE THE SLUG
        String slug = generateSlug(dto.title());

        Seller seller = sellerRepository.findSellerByUser(user)
                        .orElseThrow(()-> new ProductException("The user is not a seller", HttpStatus.FORBIDDEN));

        Category category = null;

        // IT VERIFIES IF THE CATEGORY SENT EXISTS IN DATABASE
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new ProductException("Category not found", HttpStatus.NOT_FOUND));
        }

        // IT CALCULATES THE FINAL PRICE (PRICE AFTER DISCOUNTS)
        BigDecimal finalPrice = calculateFinalPrice(dto.price(), dto.discountPercent());

        Product product = Product.builder()
                .title(dto.title())
                .slug(slug)
                .active(dto.active())
                .category(category)
                .description(dto.description())
                .price(dto.price())
                .discountPercent(dto.discountPercent())
                .finalPrice(finalPrice)
                .stockQuantity(dto.stockQuantity())
                .sku(dto.sku())
                .brand(dto.brand())
                .seller(seller)
                .build();

        // SAVE THE PRODUCT FIRST, BECAUSE ITS ID IS NEEDED TO CREATE A RELATIONSHIP
        // WITH PRODUCT IMAGES
        // DUE TO FOREIGN KEY
        Product savedProduct = productRepository.save(product);

        // IT VERIFIES IF WAS ACTUALLY SENT A IMAGE
        if (files != null && !files.isEmpty()) {

            // PARALLELSTREAM WAS USED TO SOLVE THE PROBLEM OF API TIMEOUT EXCEEDED
            // USING PARALLEL JAVA WILL TRY TO SAVE ALL THE IMAGES IN CLOUDINARY
            // AT THE SAME TIME INSTEAD OF USING A FOR LOOP THAT WOULD TAKE (N* TIME TO SEND
            // IMAGE)
            // MORE TIME
            List<ProductImage> images = files.parallelStream()
                    .map(file -> {
                        try {
                            String imageUrl = imageService.uploadImage(file);

                            return ProductImage.builder()
                                    .url(imageUrl)
                                    .product(savedProduct)
                                    .build();
                        } catch (Exception e) {
                            throw new ProductException("Error uploading product image", HttpStatus.BAD_REQUEST);
                        }
                    })
                    .toList();

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
    public Page<ProductResponseDTO> findProducts(
            String search,
            Long categoryId,
            Boolean active,
            String stockStatus,
            Pageable pageable) {
        Specification<Product> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        // IF FILTERS ARE NOT NULL, THEY ARE ADDED
        if (search != null && !search.isEmpty()) {
            spec = spec.and(ProductSpecs.hasNameLike(search));
        }

        if (categoryId != null) {
            spec = spec.and(ProductSpecs.hasCategory(categoryId));
        }

        if (active != null) {
            spec = spec.and(ProductSpecs.isActive(active));
        }

        if (stockStatus != null && !stockStatus.isEmpty()) {
            spec = spec.and(ProductSpecs.hasStockStatus(stockStatus));
        }

        Page<Product> page = productRepository.findAll(spec, pageable);

        return page.map(this::mapToResponse);
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        return mapToResponse(product);
    }

    public ProductResponseDTO update(Long id, UpdateProductDTO dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found", HttpStatus.NOT_FOUND));

        if (dto.title() != null)
            product.setTitle(dto.title());
        if (dto.description() != null)
            product.setDescription(dto.description());
        if (dto.price() != null)
            product.setPrice(dto.price());
        if (dto.discountPercent() != null)
            product.setDiscountPercent(dto.discountPercent());
        if (dto.stockQuantity() != null)
            product.setStockQuantity(dto.stockQuantity());
        if (dto.brand() != null)
            product.setBrand(dto.brand());
        if (dto.active() != null)
            product.setActive(dto.active());

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
                    product.getCategory().getParent() != null ? product.getCategory().getParent().getId() : null);
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
                imageDtos);
    }

    private BigDecimal calculateFinalPrice(BigDecimal price, Integer discount) {
        if (discount == null || discount == 0)
            return price;
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discount))
                        .divide(BigDecimal.valueOf(100)));
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