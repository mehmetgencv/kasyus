package com.kasyus.userservice.dto.responses;

import com.kasyus.userservice.model.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Response object representing a user's address")
public record AddressResponse(

        @Schema(description = "Unique identifier of the address", example = "f3d9a1f5-2c8b-4e99-9a13-42e72f9849ab")
        UUID id,

        @Schema(description = "Type of the address", example = "SHIPPING")
        AddressType type,

        @Schema(description = "Label for the address", example = "Home")
        String name,

        @Schema(description = "Whether this is the default address", example = "true")
        boolean isDefault,

        @Schema(description = "Street address", example = "123 Main Street")
        String streetAddress,

        @Schema(description = "City name", example = "Istanbul")
        String city,

        @Schema(description = "State or province", example = "Kadikoy")
        String state,

        @Schema(description = "Postal code", example = "34742")
        String postalCode,

        @Schema(description = "Country", example = "Turkey")
        String country,

        @Schema(description = "Phone number associated with the address", example = "+905551234567")
        String phone
) {}
