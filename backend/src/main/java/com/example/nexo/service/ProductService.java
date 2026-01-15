package com.example.nexo.service;

import com.example.nexo.dto.CreateProductDTO;
import com.example.nexo.dto.ProductResponseDTO;
import com.example.nexo.dto.UpdateProductDTO;
import com.example.nexo.entity.Product;
import com.example.nexo.entity.ProductImage;
import com.example.nexo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO productSlug(String slug) {
        Product product = productRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);  
    }

    public ProductResponseDTO create (CreateProductDTO dto){
        String slug = generateSlug(dto.title());
        Product product = new Product(dto.title(), dto.price(), dto.discountPercent(), dto.finalPrice(), dto.stockQuantity(), dto.brand());
        product.setSlug(slug);
        if (dto.images() != null && !dto.images().isEmpty()) {
        List<ProductImage> imageEntities = dto.images().stream()
            .map(url -> {
                ProductImage img = new ProductImage();
                img.setUrl(url);
                img.setProduct(product);
                return img;
            })
            .toList();

        product.setImages(imageEntities);
    }
        productRepository.save (product);

        return mapToResponse(product);
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

    public List<ProductResponseDTO> findProducts() {
        return productRepository.findAll() 
            .stream()
            .map(this::mapToResponse) 
            .toList();
    }

    public ProductResponseDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        return mapToResponse(product);
    }

    public ProductResponseDTO update(Long id, UpdateProductDTO dto){

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        if (dto.title() != null) product.setTitle(dto.title());
        if (dto.description() != null) product.setDescription(dto.description());
        if (dto.price() != null) product.setPrice(dto.price());
        if (dto.discountPercent() != null) product.setDiscountPercent(dto.discountPercent());
        if (dto.stockQuantity() != null) product.setStockQuantity(dto.stockQuantity());
        if (dto.brand() != null) product.setBrand(dto.brand());

        product.setFinalPrice(calculateFinalPrice(product.getPrice(), product.getDiscountPercent()));
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);

        return mapToResponse(product);
    }
    public void delete(Long id) {
        if (!productRepository.existsById(id))
            throw new RuntimeException("Produto não encontrado");

        productRepository.deleteById(id);
    }

    private ProductResponseDTO mapToResponse(Product product) {
        List<String> imageUrls = product.getImages().stream()
            .map(ProductImage::getUrl)
            .toList();
            
    return new ProductResponseDTO(
        product.getId(),
        product.getTitle(),
        product.getPrice(),
        product.getFinalPrice(),
        product.getDiscountPercent(),
        product.getStockQuantity(),
        product.getBrand(),
        product.getSlug(),
        imageUrls
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