package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.PaymentType;

import java.time.YearMonth;

public record PaymentMethodUpdateRequest(
        String name,
        PaymentType type,
        boolean isDefault,
        String provider,
        String token,
        String lastFour,
        YearMonth expiryDate
) {}