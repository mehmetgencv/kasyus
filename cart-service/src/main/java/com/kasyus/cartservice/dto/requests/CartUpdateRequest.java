package com.kasyus.cartservice.dto.requests;

public record CartUpdateRequest(Long productId, int quantity) {
}
