package com.example.nexo.repository;

import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.example.nexo.entity.product.Product;
import com.example.nexo.repository.product.ProductRepository;


@DataJpaTest // It configures H2 & JPA context
@ActiveProfiles("test") // It uses application-test.properties
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;


    // - Insere 15 produtos no banco de dados teste feito com H2
    // - Usa o method de busca dos últimos 10 produtos 
    // - primeiro garante que foram retornados 10 produtos e não todos os 15 inseridos
    // - verifica tanto pelo slug quanto pelo titulo do produto que o primeiro produto é o 15, por conta que 
    // é em ordem decrescente
    // - por ultimo pega o 6 produto inserido (10 na lista) e verifica seo slug bate

    @Test
    @DisplayName("findTop10... It must return ten last insert products in correct order")
    void findTop10ByOrderByIdDesc_ShouldReturnLast10Products() {
        for (int i = 1; i <= 15; i++) {
            createAndPersistProduct("Product " + i, "slug-" + i);
        }

        // ACT
        List<Product> result = productRepository.findTop10ByOrderByIdDesc();

        // ASSERT
        // 1. Assert that it returned 10 product, instead of the complete list (15)
        assertThat(result).hasSize(10);
        
        // 2. Order: The first product must be the 15 one
        assertThat(result.get(0).getSlug()).isEqualTo("slug-15");
        assertThat(result.get(0).getTitle()).isEqualTo("Produto 15");

        // 3. Order: The last product must be the 6 one
        assertThat(result.get(9).getSlug()).isEqualTo("slug-6");
    }


    // - Insere 12 produtos
    // - Utiliza o method para buscar os produtos aleatórios
    // - Verifica se retornou de fato 10 produtos
    // - Verifica se nenhum valor retornado é null
    @Test
    @DisplayName("findRandomProducts It must return a list limited to 10 products")
    void findRandomProducts_ShouldReturnLimitedList() {
        for(int i = 0; i < 12; i++) {
            createAndPersistProduct("Product "+ i, "slug-" + i);
        }

        List<Product> result = productRepository.findRandomProducts();
        
        assertThat(result).hasSize(10);
        assertThat(result).doesNotContainNull();
    }

    

    private void createAndPersistProduct(String title, String slug) {
        Product product = new Product();
        product.setTitle(title);
        product.setSlug(slug);
        product.setDescription("Description for tests");
        product.setPrice(new BigDecimal("100.00"));
        product.setFinalPrice(new BigDecimal("100.00"));
        product.setDiscountPercent(0);
        product.setStockQuantity(10);
        product.setBrand("Test Brand");

        // Save in H2 Test Database
        entityManager.persistAndFlush(product);
    }
    
}
