package com.kasyus.product_service.dto;

import com.kasyus.product_service.model.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Data Transfer Object representing a product")
public record ProductDto(

        @Schema(description = "Unique identifier of the product", example = "101")
        Long id,

        @Schema(description = "Name of the product", example = "Wireless Mouse")
        String name,

        @Schema(description = "Description of the product", example = "Ergonomic wireless mouse with 2.4GHz connectivity")
        String description,

        @Schema(description = "Price of the product", example = "149.99")
        BigDecimal price,

        @Schema(description = "ID of the category the product belongs to", example = "5")
        Long categoryId,

        @Schema(description = "Type of the product", example = "PHYSICAL")
        ProductType productType,

        @Schema(description = "ID of the seller offering the product", example = "10")
        Long sellerId,

        @Schema(description = "Stock Keeping Unit identifier", example = "SKU-123-ABC")
        String sku,

        @Schema(description = "Cover image URL of the product", example = "https://cdn.kasyus.com/images/cover.jpg")
        String coverImageUrl,

        @Schema(description = "List of all product image URLs")
        List<ImageDto> imageUrls
) {}
