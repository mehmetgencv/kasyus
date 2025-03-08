package com.kasyus.userservice.dto.responses;

import com.kasyus.userservice.model.enums.PaymentType;

import java.time.YearMonth;


public record PaymentMethodResponse(
        String id,
        String name,
        PaymentType type,
        boolean isDefault,
        String provider,
        String token,
        String lastFour,
        YearMonth expiryDate
) {}