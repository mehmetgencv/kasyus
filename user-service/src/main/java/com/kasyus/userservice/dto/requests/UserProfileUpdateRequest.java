package com.kasyus.userservice.dto.requests;

import java.time.LocalDate;

public record UserProfileUpdateRequest(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth,
        String customerSegment,
        Integer loyaltyPoints
) {}
