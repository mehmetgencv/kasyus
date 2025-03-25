package com.kasyus.authservice.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for logging out a user")
public record LogoutRequest(

        @Schema(description = "Access token to be invalidated", example = "eyJhbGciOiJIUzI1NiIsInR...")
        @NotBlank(message = "Access token is required")
        String accessToken,

        @Schema(description = "Refresh token to be invalidated", example = "dGhpc0lzQVRva2Vu...")
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {}
