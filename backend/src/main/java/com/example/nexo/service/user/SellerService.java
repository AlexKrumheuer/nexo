package com.example.nexo.service.user;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.nexo.dto.auth.UserResponseDTO;
import com.example.nexo.dto.seller.CreateSellerDTO;
import com.example.nexo.dto.seller.SellerResponseDTO;
import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.SellerStatus;
import com.example.nexo.entity.user.User;
import com.example.nexo.entity.user.UserRole;
import com.example.nexo.infra.exception.SellerException;
import com.example.nexo.repository.user.SellerRepository;
import com.example.nexo.repository.user.UserRepository;
import com.example.nexo.service.ImageService;

import jakarta.transaction.Transactional;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserService userService;
    public SellerService(SellerRepository sellerRepository, UserRepository userRepository, ImageService imageService, UserService userService) {
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.userService = userService;
    }

    @Transactional
    public SellerResponseDTO createSeller(CreateSellerDTO dto, MultipartFile file, User user) {
        String slug = generateSellerSlug(dto.storeName());
        User userFound = userRepository.findById(user.getId())
                .orElseThrow(() -> new SellerException("This user doesn't exists", HttpStatus.NOT_FOUND));

        if (sellerRepository.existsById(userFound.getId())) {
            throw new SellerException("This user already has a registered store.", HttpStatus.BAD_REQUEST);
        }

        Seller sellerSave = new Seller();
        sellerSave.setUser(userFound);
        sellerSave.setStoreName(dto.storeName());
        sellerSave.setSlug(slug);
        if(dto.cpf() != null) {
            sellerSave.setCpf(dto.cpf());
            sellerSave.setSellerType("PF");
        } 
        if(dto.cnpj() != null) {
            sellerSave.setCnpj(dto.cnpj());
            sellerSave.setSellerType("PJ");
        }
        if(dto.supportPhone() != null) sellerSave.setSupportPhone(dto.supportPhone());
        sellerSave.setCommissionRate(new BigDecimal("70.00"));
        sellerSave.setStatus(SellerStatus.PENDING);

        try {
            String imageUrl = imageService.uploadImage(file);
            sellerSave.setLogoUrl(imageUrl);
        } catch(Exception e) {
            throw new SellerException("Error uploading logo seller", HttpStatus.BAD_REQUEST);
        }
        if(userFound.getRole() != UserRole.ADMIN) {
            userFound.setRole(UserRole.SELLER);
        }
        
        sellerRepository.save(sellerSave);
        
        UserResponseDTO userDTO = userService.mapUser(userFound);
        return mapSeller(userDTO, sellerSave);

    }


    private String generateSellerSlug(String title) {
        String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        slug = pattern.matcher(slug).replaceAll("");

        slug = slug.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");

        String originalSlug = slug;
        int count = 1;
        while (sellerRepository.existsBySlug(slug)) {
            slug = originalSlug + "-" + count;
            count++;
        }

        return slug;
    }

    public SellerResponseDTO mapSeller(UserResponseDTO userDTO, Seller seller) {
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
}
