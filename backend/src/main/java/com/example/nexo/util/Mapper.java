package com.example.nexo.util;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.nexo.dto.auth.UserResponseDTO;
import com.example.nexo.dto.order.ResponseCartDTO;
import com.example.nexo.dto.product.CategoryResponseDTO;
import com.example.nexo.dto.product.ProductImageResponseDTO;
import com.example.nexo.dto.product.ProductResponseDTO;
import com.example.nexo.dto.seller.SellerResponseDTO;
import com.example.nexo.entity.order.Cart;
import com.example.nexo.entity.product.Category;
import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.User;
import com.example.nexo.infra.exception.ProductException;
import com.example.nexo.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Mapper {
    // THIS METHOD IS RESPONSIBLE FOR CREATING A RESPONSE PRODUCT
    // TO FRONTEND
    private final ProductRepository productRepository;

    public ProductResponseDTO MapperProductResponse(Product product) {
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

    public CategoryResponseDTO MapperCategoryResponse(Category category){
        Long parent = category.getParent() == null ? null : category.getParent().getId();
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getActive(),
                category.getImageUrl(),
                category.getDisplayOrder(),
                parent
        );
    }

    public UserResponseDTO MapperUserResponse(User user) {
        return new UserResponseDTO(user.getUsername(), user.getEmail(), user.getRole(), user.getUserBannerImage(), user.getUserPerfilImage());
    }

    public SellerResponseDTO MapperSellerResponse(UserResponseDTO userDTO, Seller seller) {
        return new SellerResponseDTO(
            userDTO, 
            seller.getStoreName(), 
            seller.getCpf(),
            seller.getCnpj(), 
            seller.getSupportPhone(),
            seller.getStatus(),
            seller.getSlug(),
            seller.getLogoUrl(),
            seller.getCommissionRate()
        );
    }

    public ResponseCartDTO MapperCartResponse(Cart cart) {
        Product product = productRepository.findById(cart.getProduct().getId())
            .orElseThrow(()-> new ProductException("Product of this cart was not found", HttpStatus.NOT_FOUND));
        ProductResponseDTO productMapped = this.MapperProductResponse(product);

        return new ResponseCartDTO(productMapped, cart.getQuantity());
    }
}
