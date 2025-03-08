package com.kasyus.userservice.dto.responses;

import com.kasyus.userservice.model.enums.AddressType;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        AddressType type,
        String name,
        boolean isDefault,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country,
        String phone
) {}