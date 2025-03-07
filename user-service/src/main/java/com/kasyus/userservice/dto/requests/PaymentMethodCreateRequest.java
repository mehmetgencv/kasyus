package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.PaymentType;

public record PaymentMethodCreateRequest(
        PaymentType type,
        boolean isDefault,
        String provider,
        String token,
        String lastFour,
        String expiryDate
) {
}
