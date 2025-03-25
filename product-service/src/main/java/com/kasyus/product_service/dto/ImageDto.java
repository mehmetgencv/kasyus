package com.kasyus.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representing a product image")
public record ImageDto(

        @Schema(description = "Unique identifier of the image", example = "1001")
        Long id,

        @Schema(description = "Public URL of the image", example = "https://cdn.kasyus.com/images/product-123.jpg")
        String imageUrl
) {}
