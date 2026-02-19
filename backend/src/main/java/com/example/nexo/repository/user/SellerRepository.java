package com.example.nexo.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nexo.entity.user.Seller;
import com.example.nexo.entity.user.User;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findSellerByUser(User user); 
}
