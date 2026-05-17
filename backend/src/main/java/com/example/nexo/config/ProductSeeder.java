package com.example.nexo.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.nexo.entity.product.Product;
import com.example.nexo.entity.product.ProductImage;
import com.example.nexo.repository.product.CategoryRepository;
import com.example.nexo.repository.product.ProductImageRepository;
import com.example.nexo.repository.product.ProductRepository;
import com.example.nexo.repository.user.SellerRepository;

import jakarta.transaction.Transactional;

@Component
public class ProductSeeder implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    private final ProductImageRepository productImageRepository;

    public ProductSeeder(ProductRepository productRepository, CategoryRepository categoryRepository,
            SellerRepository sellerRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            Product product1 = new Product();
            product1.setTitle("Women's Dunk Low");
            product1.setSlug("womens-dunk-low");
            product1.setDescription(
                    """
                            The Nike Dunk Low is a classic sneaker that has been a
                            staple in the sneaker culture for decades. With its timeless design and versatile style,
                            the Dunk Low has become a favorite among sneaker enthusiasts and fashion-forward individuals
                            alike. The Dunk Low features a low-top silhouette, a padded collar for comfort, and a durable rubber outsole for traction.
                            Whether you're hitting the streets or dressing up for a casual outing, the Nike Dunk Low is the
                            perfect choice for those who want to make a statement with their footwear.
                            """);
            product1.setCategory(categoryRepository.findById(3L).orElse(null));
            product1.setSeller(sellerRepository.findById(5L).orElse(null));
            product1.setPrice(new BigDecimal("854.99"));
            product1.setDiscountPercent(10);
            product1.setFinalPrice(new BigDecimal("769.50"));
            product1.setStockQuantity(50);
            product1.setSku("WOMENS_DUNK_LOW");
            product1.setBrand("Nike");

            productRepository.save(product1);

            ProductImage product1Image1 = new ProductImage();
            product1Image1.setProduct(product1);
            product1Image1
                    .setUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1779031130/09351355A1_ymq5ne.jpg");
            productImageRepository.save(product1Image1);

            ProductImage product1Image2 = new ProductImage();
            product1Image2.setProduct(product1);
            product1Image2
                    .setUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1779031129/09351355A5_jqtchf.jpg");
            productImageRepository.save(product1Image2);
            ProductImage product1Image3 = new ProductImage();
            product1Image3.setProduct(product1);
            product1Image3
                    .setUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1779031127/09351355A4_a0mp7r.jpg");
            productImageRepository.save(product1Image3);

            ProductImage product1Image4 = new ProductImage();
            product1Image4.setProduct(product1);
            product1Image4
                    .setUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1779031126/09351355A3_gkihxm.jpg");
            productImageRepository.save(product1Image4);

            Product product2 = new Product();
            product2.setTitle("Audi A4 Sedan Prestige Plus 2.0 TFSI 2021");
            product2.setSlug("audi-a4-sedan-prestige-plus-2021");
            product2.setDescription(
                    """
                            The Audi A4 Prestige Plus is a premium sedan that perfectly combines executive elegance with
                            sporty performance. Powered by the efficient 2.0 TFSI Turbo engine and paired with the lightning-fast
                            7-speed S-Tronic dual-clutch transmission, it delivers an exhilarating drive. Inside, you'll find the
                            state-of-the-art 12.3-inch Audi Virtual Cockpit, premium leather upholstery, a panoramic sunroof, and
                            advanced driver assistance features like adaptive cruise control. This Mythos Black metallic model offers
                            the perfect statement of luxury, technology, and German engineering.
                            """);
            product2.setCategory(categoryRepository.findById(1L).orElse(null));
            product2.setSeller(sellerRepository.findById(3L).orElse(null));
            product2.setPrice(new BigDecimal("198000.00"));
            product2.setDiscountPercent(5);
            product2.setFinalPrice(new BigDecimal("188100.00"));
            product2.setStockQuantity(1);
            product2.setSku("AUDI_A4_PPLUS_2021");
            product2.setBrand("Audi");

            productRepository.save(product2);

            ProductImage prod2Img1 = new ProductImage();
            prod2Img1.setProduct(product2);
            prod2Img1.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1778934726/nexo-products/76607ede-acf7-4f29-b74b-2d1a373f745d.jpg");
            productImageRepository.save(prod2Img1);

            ProductImage prod2Img2 = new ProductImage();
            prod2Img2.setProduct(product2);
            prod2Img2.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1778934726/nexo-products/a36c9155-782a-4f60-979c-0d5255eb09f2.jpg");
            productImageRepository.save(prod2Img2);

            ProductImage prod2Img3 = new ProductImage();
            prod2Img3.setProduct(product2);
            prod2Img3.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1778934726/nexo-products/dbc35a40-4338-4494-af56-7d72c791fb87.jpg");
            productImageRepository.save(prod2Img3);

            ProductImage prod2Img4 = new ProductImage();
            prod2Img4.setProduct(product2);
            prod2Img4.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1778934726/nexo-products/e154fb79-acc4-45e9-b9a5-988c1e2b0386.jpg");
            productImageRepository.save(prod2Img4);

            // S24 Ultra

            Product product3 = new Product();
            product3.setTitle("Samsung Galaxy S24 Ultra 512GB - Titanium Gray");
            product3.setSlug("samsung-galaxy-s24-ultra-512gb");
            product3.setDescription(
                    """
                            Welcome to the era of mobile AI. With the Galaxy S24 Ultra in your hands, you can unleash
                            whole new levels of creativity, productivity and possibility. Encased in a durable titanium shield and
                            featuring a stunning 6.8-inch flat display, it represents the pinnacle of smartphone design. The pro-grade
                            200MP camera system, enhanced by the ProVisual Engine, captures breathtaking details even in low light,
                            while the embedded S Pen lets you write, tap, and navigate with unmatched precision. Driven by the
                            Snapdragon 8 Gen 3 for Galaxy, it delivers ultimate performance for gaming and multitasking.
                            """);
            product3.setCategory(categoryRepository.findById(1L).orElse(null));
            product3.setSeller(sellerRepository.findById(4L).orElse(null));
            product3.setPrice(new BigDecimal("8999.00"));
            product3.setDiscountPercent(15);
            product3.setFinalPrice(new BigDecimal("7649.15"));
            product3.setStockQuantity(30);
            product3.setSku("SAM_S24_ULTRA_512");
            product3.setBrand("Samsung");

            productRepository.save(product3);

            // Imagens do S24 Ultra
            ProductImage prod3Img1 = new ProductImage();
            prod3Img1.setProduct(product3);
            prod3Img1.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1779031995/D_NQ_NP_2X_613707-MLA99624472498_122025-F_z3yku7.webp");
            productImageRepository.save(prod3Img1);

            ProductImage prod3Img2 = new ProductImage();
            prod3Img2.setProduct(product3);
            prod3Img2.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1779031977/D_NQ_NP_2X_699676-MLA99382879982_112025-F_tzlfs4.webp");
            productImageRepository.save(prod3Img2);

            ProductImage prod3Img3 = new ProductImage();
            prod3Img3.setProduct(product3);
            prod3Img3.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1779031975/D_NQ_NP_2X_814004-MLA100109636349_122025-F_ckbbme.webp");
            productImageRepository.save(prod3Img3);

            ProductImage prod3Img4 = new ProductImage();
            prod3Img4.setProduct(product3);
            prod3Img4.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1779031974/D_NQ_NP_2X_955698-MLA99624472512_122025-F_ka43ue.webp");
            productImageRepository.save(prod3Img4);

            // Air Pod
            Product product4 = new Product();
            product4.setTitle("Apple AirPods Pro (2nd Generation) - White");
            product4.setSlug("apple-airpods-pro-2nd-gen");
            product4.setDescription(
                    """
                            AirPods Pro feature up to two times more Active Noise Cancellation than the previous
                            generation, plus Adaptive Audio and Transparency mode to let you hear the world around you.
                            Powered by the advanced Apple H2 headphone chip, the audio quality is richer, crisper, and more immersive
                            with custom-engineered drivers. Personalized Spatial Audio with dynamic head tracking places sound all
                            around you for a theater-like experience. The MagSafe Charging Case features a built-in speaker and
                            lanyard loop, making it easier than ever to keep track of your premium audio experience.
                            """);
            product4.setCategory(categoryRepository.findById(2L).orElse(null)); 
                                                                                
            product4.setSeller(sellerRepository.findById(2L).orElse(null)); 
            product4.setPrice(new BigDecimal("2599.00"));
            product4.setDiscountPercent(12);
            product4.setFinalPrice(new BigDecimal("2287.12"));
            product4.setStockQuantity(100);
            product4.setSku("APL_AIRPODS_PRO_2");
            product4.setBrand("Apple");

            productRepository.save(product4);

            // Imagens do AirPods
            ProductImage prod4Img1 = new ProductImage();
            prod4Img1.setProduct(product4);
            prod4Img1.setUrl(
                    "https://res.cloudinary.com/dpxas5sza/image/upload/v1772029392/nexo-products/296643ba-e8b6-4e43-a7cf-8613b1e65c9a.jpg");
            productImageRepository.save(prod4Img1);
            ProductImage prod4Img2 = new ProductImage();
            prod4Img2.setProduct(product4);
            prod4Img2.setUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1772029392/nexo-products/67d08e73-f8f5-4646-8f8e-dcbbf62985b5.jpg");
            productImageRepository.save(prod4Img2);
            ProductImage prod4Img3 = new ProductImage();
            prod4Img3.setProduct(product4);
            prod4Img3.setUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1772029392/nexo-products/67d08e73-f8f5-4646-8f8e-dcbbf62985b5.jpg");
            productImageRepository.save(prod4Img3);

        }
    }

}
