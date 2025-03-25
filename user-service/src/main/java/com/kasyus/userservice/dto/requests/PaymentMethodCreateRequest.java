package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.YearMonth;

@Schema(description = "Request object for creating a new payment method")
public record PaymentMethodCreateRequest(

        @Schema(description = "Label for the payment method", example = "Personal Card")
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must be at most 50 characters")
        String name,

        @Schema(description = "Type of payment", example = "CREDIT_CARD")
        @NotNull(message = "Payment type is required")
        PaymentType type,

        @Schema(description = "Whether this is the default payment method", example = "false")
        boolean isDefault,

        @Schema(description = "Payment provider", example = "Visa")
        @NotBlank(message = "Provider is required")
        @Size(max = 50, message = "Provider must be at most 50 characters")
        String provider,

        @Schema(description = "Token representing the payment method", example = "tok_abc123xyz")
        @NotBlank(message = "Token is required")
        String token,

        @Schema(description = "Last 4 digits of the card number", example = "1234")
        @Pattern(regexp = "^[0-9]{4}$", message = "Last four digits must be 4 numeric characters")
        String lastFour,

        @Schema(description = "Expiry date of the payment method", example = "2026-08")
        @Future(message = "Expiry date must be in the future")
        @NotNull(message = "Expiry date is required")
        YearMonth expiryDate
) {}
