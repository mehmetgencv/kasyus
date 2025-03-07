package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.PaymentType;

import java.time.LocalDate;

public record PaymentMethodUpdateRequest(
        PaymentType type,
        boolean isDefault,
        String provider,
        String token,
        String lastFour,
        LocalDate expiryDate
) {}