package com.kasyus.product_service.requests;

public record CategoryUpdateRequest(
        Long id,
        String name
) {
}
