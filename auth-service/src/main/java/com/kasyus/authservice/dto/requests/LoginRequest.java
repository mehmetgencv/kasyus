package com.kasyus.authservice.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for user login")
public record LoginRequest(

        @Schema(description = "User's email address", example = "user@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "User's password", example = "SecureP@ss123")
        @NotBlank(message = "Password is required")
        String password
) {}
