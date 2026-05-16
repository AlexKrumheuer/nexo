package com.example.nexo.service.user;

import java.util.List;
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
import com.example.nexo.repository.user.UserRepository;
import com.example.nexo.util.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final Mapper mapper;
    private final UserRepository userRepository;
    
    @Transactional()
    public UserResponseDetailedDTO getAllAddress(User user) {
        List<Address> userWithAddresses = addressRepository.findAllByUser(user);

        if (!userWithAddresses.isEmpty()) {
            user.setAddresses(userWithAddresses);
        }

        return mapper.MapperUserDetailedResponse(user);
    }

    @Transactional()
    public UserResponseDetailedDTO postAddressUser(CreateUserAddressDTO dto, User userLogado) {
        User user = userRepository.findById(userLogado.getId())
        .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

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
        addressToSave.setUser(user);

        addressRepository.save(addressToSave);

        return mapper.MapperUserDetailedResponse(user);
    }

    @Transactional
    public UserResponseDetailedDTO editAddressUser(CreateUserAddressDTO dto, User userLogado) {
        User user = userRepository.findById(userLogado.getId())
        .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        Optional<Address> addressFound = addressRepository.findByUserAndZipCodeAndNumber(user, dto.zipCode(), dto.number());
        if(addressFound.isEmpty()){
            throw new UserException("Address not found for this user", HttpStatus.NOT_FOUND);
        }

        Address addressToEdit = addressFound.get();
        addressToEdit.setStreet(dto.street());
        addressToEdit.setNumber(dto.number());
        if(dto.complement() != null) addressToEdit.setComplement(dto.complement());
        addressToEdit.setCity(dto.city());
        addressToEdit.setState(dto.state());
        addressToEdit.setZipCode(dto.zipCode());
        addressToEdit.setAddressType(dto.addressType());

        addressRepository.save(addressToEdit);

        return mapper.MapperUserDetailedResponse(user);
    }

    @Transactional
    public UserResponseDetailedDTO deleteAddressUser(Long id, User userLogado) {
        User user = userRepository.findById(userLogado.getId())
        .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        Address address = addressRepository.findById(id)
        .orElseThrow(() -> new UserException("Address not found", HttpStatus.NOT_FOUND));

        if(!address.getUser().getId().equals(user.getId())){
            throw new UserException("This address doesn't belong to the logged user", HttpStatus.FORBIDDEN);
        }

        addressRepository.delete(address);

        return mapper.MapperUserDetailedResponse(user);
    }
}
