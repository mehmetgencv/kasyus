package com.kasyus.product_service.requests;


import com.kasyus.product_service.model.enums.ProductType;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        String name,
        String description,
        BigDecimal price,
        Long categoryId,
        ProductType productType,
        Long sellerId,
        String sku,
        MultipartFile image
) {
}
