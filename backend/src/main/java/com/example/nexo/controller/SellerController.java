package com.example.nexo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nexo.dto.CreateSellerDTO;
import com.example.nexo.dto.EditSellerDTO;
import com.example.nexo.entity.Seller;
import com.example.nexo.service.SellerService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin("*")
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("")
    public ResponseEntity<List<Seller>> sellers() {
        try {
            List<Seller> sellers = sellerService.getSellers();
            return ResponseEntity.ok(sellers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> sellerById(@PathVariable Long id) {
        try {
            return sellerService.getSellerById(id)
                    .map(seller -> ResponseEntity.ok(seller))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Seller> createSeller(@RequestBody @Valid CreateSellerDTO data) {
        try {
            Seller sellerSalvar = sellerService.postSeller(data);
            return ResponseEntity.ok(sellerSalvar);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seller> editSeller(@PathVariable Long id, @RequestBody @Valid EditSellerDTO data) {
        Seller seller = sellerService.editSeller(data, id);
        return ResponseEntity.ok(seller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

}
