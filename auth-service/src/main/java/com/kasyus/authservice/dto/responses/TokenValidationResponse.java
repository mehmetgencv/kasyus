package com.kasyus.authservice.dto.responses;

import com.kasyus.authservice.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response object representing the result of a token validation request")
public record TokenValidationResponse(

        @Schema(description = "Whether the token is valid", example = "true")
        boolean valid,

        @Schema(description = "Email address associated with the token", example = "user@example.com")
        String email,

        @Schema(description = "User ID extracted from the token", example = "user_123456")
        String userId,

        @Schema(description = "Roles assigned to the user", example = "[\"USER\", \"ADMIN\", \"SELLER\"]")
        List<UserRole> roles
) {}
