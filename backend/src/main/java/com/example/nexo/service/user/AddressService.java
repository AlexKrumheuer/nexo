package com.example.nexo.service.user;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nexo.dto.user.CreateUserAddressDTO;
import com.example.nexo.dto.user.UserResponseDetailedDTO;
import com.example.nexo.entity.user.Address;
import com.example.nexo.entity.user.User;
import com.example.nexo.infra.exception.UserException;
import com.example.nexo.repository.user.AddressRepository;
import com.example.nexo.util.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final Mapper mapper;
    
    @Transactional()
    public UserResponseDetailedDTO getAllAddress(CreateUserAddressDTO dto, User user) {
        addressRepository.findByUserAndZipCodeAndNumber(user, dto.zipCode(), dto.number())
            .orElseThrow(() -> new UserException("You don't have addresses inserted", HttpStatus.NOT_FOUND));

        return mapper.MapperUserDetailedResponse(user);
    }

    @Transactional()
    public UserResponseDetailedDTO postAddressUser(CreateUserAddressDTO dto, User user) {
        Optional<Address> addressFound = addressRepository.findByUserAndZipCodeAndNumber(user, dto.zipCode(), dto.number());
        if(addressFound.isPresent()){
            Address address = addressFound.get();
            if(Objects.equals(address.getComplement(), dto.complement())) {
                throw new UserException("You've already inserted this address", HttpStatus.CONFLICT);
            }
        }

        Address addressToSave = new Address();
        addressToSave.setUser(user);
        addressToSave.setStreet(dto.street());
        addressToSave.setNumber(dto.number());
        if(dto.complement() != null) addressToSave.setComplement(dto.complement());
        addressToSave.setCity(dto.city());
        addressToSave.setState(dto.state());
        addressToSave.setZipCode(dto.zipCode());
        addressToSave.setAddressType(dto.addressType());

        addressRepository.save(addressToSave);
        user.addAddress(addressToSave);

        return mapper.MapperUserDetailedResponse(user);
    }
}
