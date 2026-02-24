package com.example.nexo.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.nexo.dto.seller.CreateSellerDTO;
import com.example.nexo.dto.seller.SellerResponseDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.service.user.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SellerResponseDTO> turnSeller(
        @ModelAttribute CreateSellerDTO dto, 
        @RequestPart("file") MultipartFile logoImage, 
        Authentication auth
    ) {
        User user = (User) auth.getPrincipal();

        SellerResponseDTO createSeller = sellerService.createSeller(dto, logoImage, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createSeller);
    }
}