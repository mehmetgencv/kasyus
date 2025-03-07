package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.AddressType;

public record AddressCreateRequest(
        AddressType type,
        boolean isDefault,
        String streetAddress,
        String city,
        String state,
        String postalCode,
        String country,
        String phone
) {
}
