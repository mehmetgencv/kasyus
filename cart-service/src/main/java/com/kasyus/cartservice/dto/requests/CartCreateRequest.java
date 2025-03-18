package com.kasyus.cartservice.dto.requests;

import java.math.BigDecimal;

public record CartCreateRequest(Long productId, int quantity, BigDecimal price) {
}
