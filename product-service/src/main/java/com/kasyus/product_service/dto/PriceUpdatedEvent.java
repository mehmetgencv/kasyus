package com.kasyus.product_service.dto;

import java.math.BigDecimal;

public record PriceUpdatedEvent(
        Long productId,
        BigDecimal newPrice,
        String timestamp
) {
}
