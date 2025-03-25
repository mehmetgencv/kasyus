package com.kasyus.product_service.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Request object for updating the price of a product")
public record PriceUpdateRequest(

        @Schema(description = "New price of the product", example = "199.99")
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        BigDecimal price
) {}
