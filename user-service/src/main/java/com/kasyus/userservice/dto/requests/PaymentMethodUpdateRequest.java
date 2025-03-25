package com.kasyus.userservice.dto.requests;

import com.kasyus.userservice.model.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.YearMonth;

@Schema(description = "Request object for updating an existing payment method")
public record PaymentMethodUpdateRequest(

        @Schema(description = "Label for the payment method", example = "Business Card")
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must be at most 50 characters")
        String name,

        @Schema(description = "Type of payment", example = "DEBIT_CARD")
        @NotNull(message = "Payment type is required")
        PaymentType type,

        @Schema(description = "Whether this is the default payment method", example = "true")
        boolean isDefault,

        @Schema(description = "Payment provider", example = "Mastercard")
        @NotBlank(message = "Provider is required")
        @Size(max = 50, message = "Provider must be at most 50 characters")
        String provider,

        @Schema(description = "Token representing the payment method", example = "tok_def456uvw")
        @NotBlank(message = "Token is required")
        String token,

        @Schema(description = "Last 4 digits of the card number", example = "5678")
        @Pattern(regexp = "^[0-9]{4}$", message = "Last four digits must be 4 numeric characters")
        String lastFour,

        @Schema(description = "Expiry date of the payment method", example = "2027-05")
        @Future(message = "Expiry date must be in the future")
        @NotNull(message = "Expiry date is required")
        YearMonth expiryDate
) {}
