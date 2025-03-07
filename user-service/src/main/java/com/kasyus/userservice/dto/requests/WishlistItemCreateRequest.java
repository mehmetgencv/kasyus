package com.kasyus.userservice.dto.requests;

public record WishlistItemCreateRequest(
        String productId,
        String productName
) {}