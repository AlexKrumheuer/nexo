package com.example.nexo.service.user;

import java.math.BigDecimal;


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
import com.example.nexo.util.Mapper;
import com.example.nexo.util.SlugUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


// Static Methods
import com.example.nexo.util.SlugUtil;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final Mapper mapper;

    @Transactional
    public SellerResponseDTO createSeller(CreateSellerDTO dto, MultipartFile file, User user) {
        String slug = SlugUtil.generateSlug(dto.storeName(), sellerRepository::existsBySlug);
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
        
        UserResponseDTO userDTO = mapper.MapperUserResponse(userFound);
        return mapper.MapperSellerResponse(userDTO, sellerSave);

    }

    
}
