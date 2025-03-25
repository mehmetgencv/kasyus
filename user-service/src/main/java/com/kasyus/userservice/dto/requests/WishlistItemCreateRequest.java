package com.kasyus.userservice.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for adding a product to the user's wishlist")
public record WishlistItemCreateRequest(

        @Schema(description = "ID of the product to add", example = "prod_123abc")
        @NotBlank(message = "Product ID is required")
        String productId,

        @Schema(description = "Name of the product", example = "Wireless Headphones")
        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must be at most 100 characters")
        String productName
) {}
