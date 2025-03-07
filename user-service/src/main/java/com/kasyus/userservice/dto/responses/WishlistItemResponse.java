package com.kasyus.userservice.dto.responses;

import java.time.LocalDateTime;

public record WishlistItemResponse(
        String id,
        String productId,
        String productName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {}