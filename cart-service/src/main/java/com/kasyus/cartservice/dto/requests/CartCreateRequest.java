package com.kasyus.cartservice.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Request object for adding a product to the cart")
public record CartCreateRequest(

        @Schema(description = "ID of the product to add", example = "101")
        @NotNull(message = "Product ID is required")
        Long productId,

        @Schema(description = "Quantity of the product to add", example = "2")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        @Schema(description = "Price of the product at the time of adding to cart", example = "149.99")
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price
) {}
