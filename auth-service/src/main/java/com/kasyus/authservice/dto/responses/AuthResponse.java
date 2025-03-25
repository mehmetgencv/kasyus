package com.kasyus.authservice.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing access and refresh tokens")
public record AuthResponse(

        @Schema(description = "Access token for authorization", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        @JsonProperty("access_token")
        String accessToken,

        @Schema(description = "Refresh token to obtain new access tokens", example = "dGhpc0lzUmVmcmVzaFRva2Vu")
        @JsonProperty("refresh_token")
        String refreshToken,

        @Schema(description = "Token type (usually 'Bearer')", example = "Bearer")
        @JsonProperty("token_type")
        String tokenType
) {
    public static AuthResponse of(String accessToken, String refreshToken) {
        return new AuthResponse(accessToken, refreshToken, "Bearer");
    }
}
