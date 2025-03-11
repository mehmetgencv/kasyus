package com.kasyus.product_service.dto;

import com.kasyus.product_service.model.enums.ProductType;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Long categoryId,
        ProductType productType,
        Long sellerId,
        String sku,
        String coverImageUrl,
        List<ImageDto> imageUrls


) {
}
