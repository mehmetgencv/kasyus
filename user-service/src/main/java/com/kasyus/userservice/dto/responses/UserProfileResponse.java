package com.kasyus.userservice.dto.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserProfileResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth,
        String customerSegment,
        Integer loyaltyPoints,
        String role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {}