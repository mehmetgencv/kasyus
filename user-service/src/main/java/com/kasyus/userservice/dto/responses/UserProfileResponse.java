package com.kasyus.userservice.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response object representing a user's profile information")
public record UserProfileResponse(

        @Schema(description = "Unique identifier of the user profile", example = "user_123456")
        String id,

        @Schema(description = "User's first name", example = "Mehmet")
        String firstName,

        @Schema(description = "User's last name", example = "Genç")
        String lastName,

        @Schema(description = "User's email address", example = "mehmet@example.com")
        String email,

        @Schema(description = "User's phone number", example = "+905551234567")
        String phoneNumber,

        @Schema(description = "User's date of birth", example = "1995-08-15")
        LocalDate dateOfBirth,

        @Schema(description = "Customer segment (e.g., Premium, Standard)", example = "Premium")
        String customerSegment,

        @Schema(description = "Total loyalty points the user has", example = "120")
        Integer loyaltyPoints,

        @Schema(description = "Role of the user", example = "USER")
        String role
) {}
