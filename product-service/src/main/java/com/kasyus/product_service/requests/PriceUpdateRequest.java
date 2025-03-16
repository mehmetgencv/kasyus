package com.kasyus.product_service.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PriceUpdateRequest(
        @NotNull @Positive BigDecimal price
) {
}
