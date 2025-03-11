package com.kasyus.product_service.bulk;

import com.kasyus.product_service.dto.ImageDto;
import com.kasyus.product_service.model.enums.ProductType;

import java.math.BigDecimal;
import java.util.List;

public record BulkProductCreateRequest(
        String name,
        String description,
        BigDecimal price,
        Long categoryId,
        ProductType productType,
        Long sellerId,
        String sku,
        List<String> imageUrls

) {
        }