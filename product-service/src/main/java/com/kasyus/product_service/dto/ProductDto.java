package com.kasyus.product_service.dto;

import com.kasyus.product_service.model.enums.ProductType;

import java.math.BigDecimal;

public record ProductDto(
        String name,
        String description,
        BigDecimal price,
        Long categoryId,
        ProductType productType,
        Long sellerId,
        String sku,
        String createdBy,
        String updatedBy


) {
}
