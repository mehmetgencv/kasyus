package com.kasyus.userservice.dto.responses;

import com.kasyus.userservice.model.enums.AddressType;

public record AddressResponse(
        String id,
        AddressType type,
        boolean isDefault,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country,
        String phone
) {}