package com.example.nexo.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nexo.dto.user.CreateUserAddressDTO;
import com.example.nexo.dto.user.UserResponseDetailedDTO;
import com.example.nexo.entity.user.User;
import com.example.nexo.service.user.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDetailedDTO> postAddressUser(@RequestBody @Valid CreateUserAddressDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        UserResponseDetailedDTO userWithAddress = addressService.postAddressUser(dto, user);
        return ResponseEntity.ok(userWithAddress);
    }

    @GetMapping("")
    public ResponseEntity<UserResponseDetailedDTO> getAddressUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        UserResponseDetailedDTO userWithAddress = addressService.getAllAddress(user);
        return ResponseEntity.ok(userWithAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDetailedDTO> updateAddressUser(@RequestBody @Valid CreateUserAddressDTO dto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        UserResponseDetailedDTO userWithAddress = addressService.editAddressUser(dto, user);
        return ResponseEntity.ok(userWithAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDetailedDTO> deleteAddressUser(@PathVariable Long id, Authentication auth) {
        User user = (User) auth.getPrincipal();
        UserResponseDetailedDTO userWithAddress = addressService.deleteAddressUser(id, user);
        return ResponseEntity.ok(userWithAddress);  
    }
}
