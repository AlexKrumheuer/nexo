package com.example.nexo.service;

import com.example.nexo.dto.CreateProductDTO;
import com.example.nexo.dto.ProductResponseDTO;
import com.example.nexo.dto.UpdateProductDTO;
import com.example.nexo.entity.Product;
import com.example.nexo.entity.ProductImage;
import com.example.nexo.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // It allows mockito
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("It must create the product, link the images and generate slug correctly")
    void create_ShouldSaveProductWithImagesAndSlug() {
        // Create a product
        CreateProductDTO dto = new CreateProductDTO(
                "Fone Sony",
                "Melhor fone",
                new BigDecimal("100.00"),
                0,
                null,
                10,
                "Sony",
                List.of("http://img1.jpg", "http://img2.jpg"));

        // Ensinamos o Mock: "Quando perguntarem se o slug 'fone-sony' existe, diga NÃO"
        when(productRepository.existsBySlug("fone-sony")).thenReturn(false);

        // Once it doesnt have a real database, it wont receive any id given by
        // GeneratedValue(strategy = GenerationType.IDENTITY)
        // It simulates an Id
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        // --- ACT ---
        ProductResponseDTO response = productService.create(dto);

        // --- ASSERT ---

        // 1. It validates the response given to controller
        assertThat(response).isNotNull();
        assertThat(response.slug()).isEqualTo("fone-sony"); // It validates that the slug was rightly generated
        assertThat(response.images()).hasSize(2);

        // 2. (ArgumentCaptor)
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productCaptor.capture()); // Capture the product object before being saved in db
        Product capturedProduct = productCaptor.getValue();

        // It validates that Product and ProductImage tables were correctly linked
        // and the value saved in db is what it was sent
        assertThat(capturedProduct.getImages()).hasSize(2);
        assertThat(capturedProduct.getImages().get(0).getUrl()).isEqualTo("http://img1.jpg");

        // This line validates if it's possible to achieve parent object (Product) using
        // the child
        // object (ProductImage). It's done like this, because It's the child who orders
        // the foreign key
        // relationship. So, if it can be accessed, it means that the object is
        // structured according to
        // what java expects
        assertThat(capturedProduct.getImages().get(0).getProduct()).isEqualTo(capturedProduct);
    }

    @Test
    @DisplayName("Create: Should increment slug when already exists")
    void create_ShouldIncrementSlug_WhenExists() {
        CreateProductDTO dto = new CreateProductDTO(
            "Iphone", "Desc", BigDecimal.TEN, 0, null, 10, "Apple", null
        );


        when(productRepository.existsBySlug("iphone")).thenReturn(true);
        when(productRepository.existsBySlug("iphone-1")).thenReturn(true);
        when(productRepository.existsBySlug("iphone-2")).thenReturn(false);

        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        ProductResponseDTO response = productService.create(dto);

        assertThat(response.slug()).isEqualTo("iphone-2");
        
        verify(productRepository, times(3)).existsBySlug(anyString());
    }

    // Validate if the product's content find using repository is equal to the one
    // found using service
    @Test
    @DisplayName("ProductSlug: It must return DTO when found")
    void productSlug_WhenFound_ReturnDTO() {
        Product product = createFakeProduct(1L);
        when(productRepository.findBySlug("slug-test")).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.productSlug("slug-test");

        assertThat(result.title()).isEqualTo(product.getTitle());
        assertThat(result.slug()).isEqualTo(product.getSlug());
        verify(productRepository).findBySlug("slug-test");
    }

    // Create 3 products
    // Guarantee that the list returned by service has length of 3
    // Guarantee that the id of the first list's product is 1
    // Guarantee that the image returned is the String passed when the object was
    // created
    // Guarantee that the method called was findRandomProducts
    @Test
    @DisplayName("randomProducts: It must return a list of random Products")
    void randomProduct_WhenFound_ReturnDTOOfRandomProducts() {
        Product p1 = createFakeProduct(1L);
        Product p2 = createFakeProduct(2L);
        Product p3 = createFakeProduct(3L);

        when(productRepository.findRandomProducts()).thenReturn(List.of(p1, p2, p3));

        List<ProductResponseDTO> result = productService.randomProducts();

        assertThat(result).hasSize(3);
        ProductResponseDTO dto1 = result.get(0);
        assertThat(dto1.id()).isEqualTo(1L);
        assertThat(dto1.title()).isEqualTo("Original Product");
        assertThat(dto1.images().get(0)).isEqualTo("http://url.com");

        verify(productRepository).findRandomProducts();
    }

    // Create 3 products
    // Guarantee that the list returned by service has length of 3
    // Guarantee that the id of the first list's product is 3, cause It's ordered by
    // desc
    // Guarantee that the image returned is the String passed when the object was
    // created
    // Guarantee that the method called was findTop10ByOrderByIdDesc

    @Test
    @DisplayName("lastProducts: It must return a list of ProductResponseDTO")
    void lastProducts_WhenFound_ReturnDTOOfProduct() {
        Product p1 = createFakeProduct(1L);
        Product p2 = createFakeProduct(2L);
        Product p3 = createFakeProduct(3L);

        when(productRepository.findTop10ByOrderByIdDesc()).thenReturn(List.of(p3, p2, p1));

        List<ProductResponseDTO> result = productService.lastProducts();

        assertThat(result).hasSize(3);
        ProductResponseDTO dto1 = result.get(0);
        assertThat(dto1.id()).isEqualTo(3L);
        assertThat(dto1.title()).isEqualTo("Original Product");
        assertThat(dto1.images().get(0)).isEqualTo("http://url.com");

        verify(productRepository).findTop10ByOrderByIdDesc();
    }

    // Create 3 products
    // Guarantee that the list returned by service has length of 3
    // Guarantee that the id of the first list's product is 1
    // Guarantee that the image returned is the String passed when the object was
    // created
    // Guarantee that the method called was findAll

    @Test
    @DisplayName("findProducts: It must return a list of all Products")
    void findProducts_WhenFound_ReturnDTOOfProduct() {
        Product p1 = createFakeProduct(1L);
        Product p2 = createFakeProduct(2L);
        Product p3 = createFakeProduct(3L);

        when(productRepository.findAll()).thenReturn(List.of(p1, p2, p3));

        List<ProductResponseDTO> result = productService.findProducts();

        assertThat(result).hasSize(3);
        ProductResponseDTO dto1 = result.get(0);
        assertThat(dto1.id()).isEqualTo(1L);
        assertThat(dto1.title()).isEqualTo("Original Product");
        assertThat(dto1.images().get(0)).isEqualTo("http://url.com");

        verify(productRepository).findAll();
    }

    // Create 1 product
    // Validate if the content of the product returned by repository is the same as
    // the one returned by service

    @Test
    @DisplayName("findById: It must return a DTO of a Product using its Id")
    void findProductById_WhenFound_ReturnDTOOfProduct() {
        Product p1 = createFakeProduct(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(p1));

        ProductResponseDTO result = productService.findById(1L);

        assertThat(result.id()).isEqualTo(p1.getId());
        assertThat(result.title()).isEqualTo(p1.getTitle());
        assertThat(result.images().get(0)).isEqualTo(p1.getImages().get(0).getUrl());

        verify(productRepository).findById(1L);
    }

    // Validate if when the product is not found the app returns a RuntimeException with 
    // Product not found message

    @Test
    @DisplayName("findById: Should throw exception when product not found")
    void findProductById_WhenNotFound_ThrowException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(RuntimeException.class) 
                .hasMessage("Product not found");
        verify(productRepository).findById(99L);
    }


    @Test
    @DisplayName("Update: It mustn't upadte null fields")
    void update_ShouldUpdateOnlyNonNullFields() {
        // Product before updates
        Product beforeUpdateProduct = createFakeProduct(1L);

        // Test updates
        UpdateProductDTO dto = new UpdateProductDTO(
            "New Title",      // before = Original Product    
            null,                 
            new BigDecimal("200.00"), // before = 10
            50,      // before = 0
            null,
            null
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(beforeUpdateProduct));
        
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        // --- 2. ACT ---
        productService.update(1L, dto);

        // --- 3. ASSERT  ---
        // Get the exactly product before saving
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        Product updatedProduct = captor.getValue();

        // 1 Validation: Updates were done?
        assertThat(updatedProduct.getTitle()).isEqualTo("New Title");
        assertThat(updatedProduct.getPrice()).isEqualTo(new BigDecimal("200.00"));
        assertThat(updatedProduct.getDiscountPercent()).isEqualTo(50);



        // 2 Validation: Price was recalculated?
        assertThat(updatedProduct.getFinalPrice()).isEqualByComparingTo("100.00");

        // 3 Validation: updated date was updated
        assertThat(updatedProduct.getUpdatedAt()).isNotNull(); 
    }

    @Test
    @DisplayName("Update: Should throw exception when product not found")
    void update_WhenNotFound_ThrowException() {
        UpdateProductDTO dto = new UpdateProductDTO(null, null, null, null, null, null);
        
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(99L, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found");
    }

    @Test
    @DisplayName("Delete: Should delete product when exists")
    void delete_WhenExists_ShouldDelete() {
        // ARRANGE
        when(productRepository.existsById(1L)).thenReturn(true);

        // ACT
        productService.delete(1L);

        // ASSERT
        verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Delete: Should throw exception when id not exists")
    void delete_WhenNotExists_ShouldThrowException() {
        // ARRANGE
        when(productRepository.existsById(1L)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> productService.delete(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found"); 

        verify(productRepository, never()).deleteById(any());
    }

    private Product createFakeProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setTitle("Original Product");
        product.setSlug("slug-test");
        product.setPrice(BigDecimal.TEN);
        product.setFinalPrice(BigDecimal.TEN);
        product.setDiscountPercent(0);
        product.setImages(List.of(new ProductImage(1L, "http://url.com", product)));
        return product;
    }

}