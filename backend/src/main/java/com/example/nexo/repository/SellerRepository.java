package com.example.nexo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nexo.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    public Seller findByEmail(String email);   
}
