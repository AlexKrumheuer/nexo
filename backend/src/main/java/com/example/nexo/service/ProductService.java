package com.example.nexo.service;

import com.example.nexo.dto.CreateProductDTO;
import com.example.nexo.dto.ProductResponseDTO;
import com.example.nexo.dto.UpdateProductDTO;
import com.example.nexo.entity.Product;
import com.example.nexo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDTO create (CreateProductDTO dto){
        Product Product = new Product();

        Product.setTitle(dto.title());
        Product.setDescription(dto.description());
        Product.setPrice(dto.price());
        Product.setDiscountPercent(dto.discountPercent());
        Product.setBrand(dto.brand());

        Product.setSlug(generateSlug(dto.title()));
        Product.setPrice(calculateFinalPrice(dto.price(), dto.discountPercent()));
        Product.setCreatedAt(LocalDateTime.now());
        Product.setUpdatedAt(LocalDateTime.now());

        productRepository.save (Product);

        return mapToResponse(Product);
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
        return new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getFinalPrice(),
                product.getDiscountPercent(),
                product.getStockQuantity(),
                product.getBrand()
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

    private String generateSlug(String title){
      String slug = title.toLowerCase().replace("", "-");
      int i = 1;
      while (productRepository.existsBySlug(slug)) {
        slug = slug + "-" + i++;
      }
      return slug;
    }
}