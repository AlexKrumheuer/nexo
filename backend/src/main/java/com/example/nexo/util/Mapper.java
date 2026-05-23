package com.example.nexo.util;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.nexo.dto.order.ResponseCartDTO;
import com.example.nexo.dto.product.CategoryResponseDTO;
import com.example.nexo.dto.product.OrderItemResponseDTO;
import com.example.nexo.dto.product.OrderResponseDTO;
import com.example.nexo.dto.product.ProductImageResponseDTO;
import com.example.nexo.dto.product.ProductResponseDTO;
import com.example.nexo.dto.seller.SellerResponseDTO;
import com.example.nexo.dto.user.AddressResponseDTO;
import com.example.nexo.dto.user.UserResponseDTO;
import com.example.nexo.dto.user.UserResponseDetailedDTO;
import com.example.nexo.entity.order.Cart;
import com.example.nexo.entity.product.Category;
import com.example.nexo.entity.product.Order;
import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.user.Address;
import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Mapper {
    // THIS METHOD IS RESPONSIBLE FOR CREATING A RESPONSE PRODUCT
    // TO FRONTEND
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
        
        return new UserResponseDTO(
            user.getUsername(), 
            user.getEmail(), 
            user.getRole(), 
            user.getUserBannerImage(), 
            user.getUserPerfilImage()
        );
    }

    public UserResponseDetailedDTO MapperUserDetailedResponse(User user) {
        List<AddressResponseDTO> addressDtos = user.getAddresses() != null
            ? user.getAddresses().stream().map(this::MapperAddressResponse).toList()
            : Collections.emptyList(); 
        
        return new UserResponseDetailedDTO(
            user.getUsername(), 
            user.getEmail(), 
            user.getRole(), 
            user.getUserBannerImage(), 
            user.getUserPerfilImage(),
            addressDtos
        );
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

        ProductResponseDTO productMapped = this.MapperProductResponse(cart.getProduct());

        return new ResponseCartDTO(productMapped, cart.getQuantity());
    }

    public AddressResponseDTO MapperAddressResponse(Address address) {
        return new AddressResponseDTO(
            address.getId(),
            address.getStreet(), 
            address.getNumber(), 
            address.getComplement(), 
            address.getNeighborhood(),
            address.getCity(), 
            address.getState(), 
            address.getZipCode(), 
            address.getAddressType()
        );
    }

    public OrderResponseDTO MapperOrderResponse(Order order) {
        List<OrderItemResponseDTO> itemDtos = order.getOrderList() != null
            ? order.getOrderList().stream()
                .map(item -> new OrderItemResponseDTO(
                    item.getOrder().getId(),
                    this.MapperProductResponse(item.getProduct()),
                    item.getQuantity()
                ))
                .toList()
            : Collections.emptyList();

        return new OrderResponseDTO(
            itemDtos,
            order.getSubtotal(),
            order.getShippingPrice(),
            order.getDiscountPrice(),
            order.getTotalPrice(),
            order.getStatus().name(),
            order.getPaymentMethod().name(),
            order.getShippingStreet(),
            order.getShippingNumber(),
            order.getShippingComplement(),
            order.getShippingNeighborhood(),
            order.getShippingCity(),
            order.getShippingState(),
            order.getShippingZipCode(),

            order.getCreatedAt(),
            order.getUpdatedAt()
        );
    }
}
