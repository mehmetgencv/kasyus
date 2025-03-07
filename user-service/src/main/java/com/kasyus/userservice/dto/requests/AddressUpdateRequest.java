package com.kasyus.userservice.dto.requests;


import com.kasyus.userservice.model.enums.AddressType;

public record AddressUpdateRequest(
        AddressType type,
        boolean isDefault,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country,
        String phone
) {}