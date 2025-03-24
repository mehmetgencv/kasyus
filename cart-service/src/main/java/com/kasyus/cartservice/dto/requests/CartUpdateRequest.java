package com.kasyus.cartservice.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request object for updating the quantity of a product in the cart")
public record CartUpdateRequest(

        @Schema(description = "ID of the product to update", example = "101")
        @NotNull(message = "Product ID is required")
        Long productId,

        @Schema(description = "New quantity of the product", example = "3")
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {}
