package com.kasyus.cartservice.dto;

import java.math.BigDecimal;

public record PriceUpdatedEvent(
        Long productId,
        BigDecimal newPrice,
        String timestamp
) {
}
