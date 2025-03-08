package com.kasyus.userservice.service.Impl;


import com.kasyus.userservice.dto.requests.AddressCreateRequest;
import com.kasyus.userservice.dto.requests.AddressUpdateRequest;
import com.kasyus.userservice.dto.responses.AddressResponse;
import com.kasyus.userservice.exception.AddressNotFoundException;
import com.kasyus.userservice.exception.UserNotFoundException;
import com.kasyus.userservice.mapper.AddressMapper;
import com.kasyus.userservice.model.Address;
import com.kasyus.userservice.model.User;
import com.kasyus.userservice.repository.AddressRepository;
import com.kasyus.userservice.repository.UserRepository;
import com.kasyus.userservice.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    @Transactional
    public String addAddress(String userId, AddressCreateRequest request) {
        User user = getUserEntity(userId);
        Address address = addressMapper.toAddress(request);

        address.setUser(user);
        user.getAddresses().add(address);

        userRepository.save(user);

        logger.info("Address added to user: {}", userId);
        return "ok";
    }


    @Override
    public Set<AddressResponse> getAddresses(String userId) {
        User user = getUserEntity(userId);
        return user.getAddresses().stream()
                .map(addressMapper::toAddressResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void updateAddress(String userId, String addressId, AddressUpdateRequest request) {
        User user = getUserEntity(userId);
        Address address = getAddressEntity(user, addressId);
        address.setType(request.type());
        address.setName(request.name());
        address.setDefault(request.isDefault());
        address.setStreetAddress(request.streetAddress());
        address.setCity(request.city());
        address.setState(request.state());
        address.setPostalCode(request.postalCode());
        address.setCountry(request.country());
        address.setPhone(request.phone());
        userRepository.save(user);
        logger.info("Address updated for user: {}, addressId: {}", userId, addressId);
    }

    @Override
    @Transactional
    public void deleteAddress(String userId, String addressId) {
        User user = getUserEntity(userId);
        Address address =  getAddressEntity(user, addressId);
        user.getAddresses().remove(address);
        userRepository.save(user);
        logger.info("Address deleted for user: {}, addressId: {}", userId, addressId);
    }

    private Address getAddressEntity(User user, String addressId) {
        return user.getAddresses().stream()
                .filter(a -> a.getId().toString().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new AddressNotFoundException("Address not found: " + addressId));
    }

    private User getUserEntity(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}