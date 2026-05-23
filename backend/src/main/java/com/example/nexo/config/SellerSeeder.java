package com.example.nexo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.SellerStatus;
import com.example.nexo.entity.user.User;
import com.example.nexo.entity.user.UserRole;
import com.example.nexo.repository.user.SellerRepository;
import com.example.nexo.repository.user.UserRepository;

import jakarta.transaction.Transactional;

@Component
@Order(2)
public class SellerSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SellerRepository sellerRepository;

    public SellerSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.sellerRepository = sellerRepository;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (sellerRepository.count() == 0) {
            //Seller Apple
            User apple = new User(
                    "Apple Inc.",
                    "apple@apple.com",
                    passwordEncoder.encode("apple123"));
            apple.setRole(UserRole.SELLER);
            userRepository.save(apple);

            Seller appleSeller = new Seller();
            appleSeller.setUser(apple);
            appleSeller.setStoreName("Apple Inc.");
            appleSeller.setSlug("apple");
            appleSeller.setCnpj("89161779000130");
            appleSeller.setSellerType("PJ");
            appleSeller.setSupportPhone("91997175668");
            appleSeller.setLogoUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1778933131/nexo-products/acf0d19b-e4fc-4b0f-b064-a37313416d86.png");
            appleSeller.setStatus(SellerStatus.ACTIVE);

            sellerRepository.save(appleSeller); 

            

            //Seller Audi
            User audi = new User(
                    "Audi AG",
                    "audi@audi.com",
                    passwordEncoder.encode("audi123"));
            audi.setRole(UserRole.SELLER);
            userRepository.save(audi);

            Seller audiSeller = new Seller();
            audiSeller.setUser(audi);
            audiSeller.setStoreName("Audi AG");
            audiSeller.setSlug("audi");
            audiSeller.setCnpj("57045035000164");
            audiSeller.setSellerType("PJ");
            audiSeller.setSupportPhone("67985707936");
            audiSeller.setLogoUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1779029346/image-removebg-preview_1_qsyjbr.png");
            audiSeller.setStatus(SellerStatus.ACTIVE);

            sellerRepository.save(audiSeller); 


            //Seller Samsung
            User samsung = new User(
                    "Samsung Electronics",
                    "samsung@samsung.com",
                    passwordEncoder.encode("samsung123"));
            samsung.setRole(UserRole.SELLER);
            userRepository.save(samsung);

            Seller samsungSeller = new Seller();
            samsungSeller.setUser(samsung);
            samsungSeller.setStoreName("Samsung Electronics");
            samsungSeller.setSlug("samsung");
            samsungSeller.setCnpj("69976244000100");
            samsungSeller.setSellerType("PJ");
            samsungSeller.setSupportPhone("84992232945");
            samsungSeller.setLogoUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1779029265/image-removebg-preview_cxngur.png");
            samsungSeller.setStatus(SellerStatus.ACTIVE);

            sellerRepository.save(samsungSeller); 

            //Seller Nike
            User nike = new User(
                    "Nike Inc.",
                    "nike@nike.com",
                    passwordEncoder.encode("nike123"));
            nike.setRole(UserRole.SELLER);
            userRepository.save(nike);

            Seller nikeSeller = new Seller();
            nikeSeller.setUser(nike);
            nikeSeller.setStoreName("Nike Inc.");
            nikeSeller.setSlug("nike");
            nikeSeller.setCnpj("27762742000100");
            nikeSeller.setSellerType("PJ");
            nikeSeller.setSupportPhone("96998222315");
            nikeSeller.setLogoUrl("https://res.cloudinary.com/dpxas5sza/image/upload/v1779029486/image-removebg-preview_2_miugem.png");
            nikeSeller.setStatus(SellerStatus.ACTIVE);

            sellerRepository.save(nikeSeller); 
            
            
        }
    }

}
