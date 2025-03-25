package com.kasyus.userservice.dto.responses;

import com.kasyus.userservice.model.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.YearMonth;

@Schema(description = "Response object representing a saved payment method")
public record PaymentMethodResponse(

        @Schema(description = "Unique identifier of the payment method", example = "pm_123abc456")
        String id,

        @Schema(description = "Name/label of the payment method", example = "Personal Visa")
        String name,

        @Schema(description = "Type of the payment method", example = "CREDIT_CARD")
        PaymentType type,

        @Schema(description = "Whether this payment method is set as default", example = "true")
        boolean isDefault,

        @Schema(description = "Payment provider", example = "Visa")
        String provider,

        @Schema(description = "Secure token representing the payment method", example = "tok_abc123xyz")
        String token,

        @Schema(description = "Last four digits of the card number", example = "4242")
        String lastFour,

        @Schema(description = "Expiry date of the card", example = "2026-12")
        YearMonth expiryDate
) {}
