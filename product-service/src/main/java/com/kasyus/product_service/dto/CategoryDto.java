package com.kasyus.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Data Transfer Object representing a product category")
public record CategoryDto(

        @Schema(description = "Unique identifier of the category", example = "1")
        Long id,

        @Schema(description = "Name of the category", example = "Electronics")
        String name,

        @Schema(description = "List of products belonging to the category")
        List<ProductDto> products
) {}
