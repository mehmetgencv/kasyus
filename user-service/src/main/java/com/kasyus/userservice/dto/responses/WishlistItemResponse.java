package com.kasyus.userservice.dto.responses;


public record WishlistItemResponse(
        String id,
        String productId,
        String productName
) {}