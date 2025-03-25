package com.kasyus.product_service.requests;

import com.kasyus.product_service.model.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Schema(description = "Request object for updating an existing product")
public record ProductUpdateRequest(

        @Schema(description = "Updated name of the product", example = "Wireless Keyboard")
        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must be at most 100 characters")
        String name,

        @Schema(description = "Updated description of the product", example = "Slim wireless keyboard with silent keys")
        @NotBlank(message = "Product description is required")
        String description,

        @Schema(description = "Updated price of the product", example = "129.99")
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price,

        @Schema(description = "Updated category ID", example = "6")
        @NotNull(message = "Category ID is required")
        Long categoryId,

        @Schema(description = "Updated product type", example = "DIGITAL")
        @NotNull(message = "Product type is required")
        ProductType productType,

        @Schema(description = "ID of the seller", example = "10")
        @NotNull(message = "Seller ID is required")
        Long sellerId,

        @Schema(description = "Updated SKU", example = "SKU-456-ABC")
        @NotBlank(message = "SKU is required")
        @Size(max = 50, message = "SKU must be at most 50 characters")
        String sku,

        @Schema(description = "Image file to upload", type = "string", format = "binary")
        MultipartFile image
) {}
