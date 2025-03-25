package com.kasyus.userservice.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "User profile update request payload")
public record UserProfileUpdateRequest(

        @Schema(description = "First name of the user", example = "Mehmet")
        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must be at most 50 characters")
        String firstName,

        @Schema(description = "Last name of the user", example = "Genc")
        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must be at most 50 characters")
        String lastName,

        @Schema(description = "Email address", example = "mehmet@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @Schema(description = "Phone number", example = "+905551234567")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid")
        String phoneNumber,

        @Schema(description = "Date of birth", example = "1995-08-20")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @Schema(description = "Customer segment", example = "Premium")
        @Size(max = 30, message = "Customer segment must be at most 30 characters")
        String customerSegment,

        @Schema(description = "Loyalty points of the user", example = "150")
        @Min(value = 0, message = "Loyalty points cannot be negative")
        Integer loyaltyPoints
) {}
