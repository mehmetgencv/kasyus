package com.kasyus.userservice.dto.responses;

import com.kasyus.userservice.model.enums.PaymentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PaymentMethodResponse(
        String id,
        PaymentType type,
        boolean isDefault,
        String provider,
        String token,
        String lastFour,
        LocalDate expiryDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {}