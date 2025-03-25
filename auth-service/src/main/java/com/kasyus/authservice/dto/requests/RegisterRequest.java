package com.kasyus.authservice.dto.requests;

import com.kasyus.authservice.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for user registration")
public record RegisterRequest(

        @Schema(description = "User's first name", example = "Mehmet")
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @Schema(description = "User's last name", example = "Genç")
        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @Schema(description = "User's email address", example = "mehmet@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "User's password (must be at least 8 characters)", example = "StrongP@ssword123")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @Schema(description = "Role to assign to the user", example = "USER")
        @NotNull(message = "Role is required")
        UserRole role
) {}
