package com.example.nexo.repository.user;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nexo.entity.user.Address;
import com.example.nexo.entity.user.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
    Optional<Address> findByUserAndZipCodeAndNumber(User user, String zipCode, String number);
    List<Address> findAllByUser(User user);
}
