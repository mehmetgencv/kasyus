package com.kasyus.userservice.service;

import com.kasyus.userservice.dto.requests.AddressCreateRequest;
import com.kasyus.userservice.dto.requests.AddressUpdateRequest;
import com.kasyus.userservice.dto.responses.AddressResponse;

import java.util.Set;

public interface AddressService {
    String addAddress(String userId, AddressCreateRequest request);
    Set<AddressResponse> getAddresses(String userId);
    void updateAddress(String userId, String addressId, AddressUpdateRequest request);
    void deleteAddress(String userId, String addressId);
}