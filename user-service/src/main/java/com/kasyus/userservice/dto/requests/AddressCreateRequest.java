package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Request object for creating a new address")
public record AddressCreateRequest(

        @Schema(description = "Type of the address", example = "SHIPPING")
        @NotNull(message = "Address type is required")
        AddressType type,

        @Schema(description = "Label for the address (e.g., Home, Office)", example = "Home")
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must be at most 50 characters")
        String name,

        @Schema(description = "Whether this is the default address", example = "true")
        boolean isDefault,

        @Schema(description = "Street address", example = "123 Main St")
        @NotBlank(message = "Street address is required")
        @Size(max = 100, message = "Street address must be at most 100 characters")
        String streetAddress,

        @Schema(description = "City name", example = "Istanbul")
        @NotBlank(message = "City is required")
        @Size(max = 50, message = "City must be at most 50 characters")
        String city,

        @Schema(description = "State or province", example = "Kadikoy")
        @NotBlank(message = "State is required")
        @Size(max = 50, message = "State must be at most 50 characters")
        String state,

        @Schema(description = "Postal code", example = "34000")
        @NotBlank(message = "Postal code is required")
        @Size(max = 20, message = "Postal code must be at most 20 characters")
        String postalCode,

        @Schema(description = "Country", example = "Türkiye")
        @NotBlank(message = "Country is required")
        @Size(max = 50, message = "Country must be at most 50 characters")
        String country,

        @Schema(description = "Phone number with country code", example = "+905551234567")
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid")
        String phone
) {}
