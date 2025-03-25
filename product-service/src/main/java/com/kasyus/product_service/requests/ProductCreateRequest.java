package com.kasyus.product_service.requests;

import com.kasyus.product_service.model.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Request object for creating a new product")
public record ProductCreateRequest(

        @Schema(description = "Name of the product", example = "Wireless Mouse")
        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must be at most 100 characters")
        String name,

        @Schema(description = "Description of the product", example = "Ergonomic wireless mouse with USB receiver")
        @NotBlank(message = "Product description is required")
        String description,

        @Schema(description = "Price of the product", example = "89.99")
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @Schema(description = "ID of the category the product belongs to", example = "5")
        @NotNull(message = "Category ID is required")
        Long categoryId,

        @Schema(description = "Type of the product", example = "PHYSICAL")
        @NotNull(message = "Product type is required")
        ProductType productType,

        @Schema(description = "ID of the seller", example = "10")
        @NotNull(message = "Seller ID is required")
        Long sellerId,

        @Schema(description = "Stock keeping unit", example = "SKU-123-XYZ")
        @NotBlank(message = "SKU is required")
        @Size(max = 50, message = "SKU must be at most 50 characters")
        String sku
) {}
