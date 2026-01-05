package com.example.nexo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.nexo.dto.CreateSellerDTO;
import com.example.nexo.dto.EditSellerDTO;
import com.example.nexo.entity.Seller;
import com.example.nexo.repository.SellerRepository;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        if(sellerRepository.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found"); 
        }
        return sellerRepository.findById(id);
    }

    public Seller postSeller(CreateSellerDTO data) {
        if(sellerRepository.findByEmail(data.email()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This e-mail was already taken");        
        }
        Seller seller = new Seller(data.companyName(), data.email(), data.password(), data.role());
        return sellerRepository.save(seller);
    }

    public Seller editSeller(EditSellerDTO data, Long id) {
        Seller sellerExist = sellerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found"));
        sellerExist.setCompanyName(data.companyName());
        sellerExist.setEmail(data.email());
        sellerExist.setPassword(data.password());
        return sellerRepository.save(sellerExist);
    }

    public void deleteSeller(Long id) {
        if(!sellerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found");        
        }
        sellerRepository.deleteById(id);
    }

}
