package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Request object for updating an existing address")
public record AddressUpdateRequest(

        @Schema(description = "Type of the address", example = "BILLING")
        @NotNull(message = "Address type is required")
        AddressType type,

        @Schema(description = "Label for the address (e.g., Home, Office)", example = "Work")
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must be at most 50 characters")
        String name,

        @Schema(description = "Whether this is the default address", example = "false")
        boolean isDefault,

        @Schema(description = "Street address", example = "456 Business Ave")
        @NotBlank(message = "Street address is required")
        @Size(max = 100, message = "Street address must be at most 100 characters")
        String streetAddress,

        @Schema(description = "City name", example = "Ankara")
        @NotBlank(message = "City is required")
        @Size(max = 50, message = "City must be at most 50 characters")
        String city,

        @Schema(description = "State or province", example = "Cankaya")
        @NotBlank(message = "State is required")
        @Size(max = 50, message = "State must be at most 50 characters")
        String state,

        @Schema(description = "Postal code", example = "06000")
        @NotBlank(message = "Postal code is required")
        @Size(max = 20, message = "Postal code must be at most 20 characters")
        String postalCode,

        @Schema(description = "Country", example = "Turkey")
        @NotBlank(message = "Country is required")
        @Size(max = 50, message = "Country must be at most 50 characters")
        String country,

        @Schema(description = "Phone number with country code", example = "+903123456789")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid")
        String phone
) {}
