package com.kasyus.userservice.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object representing an item in the user's wishlist")
public record WishlistItemResponse(

        @Schema(description = "Unique identifier of the wishlist item", example = "wish_789xyz")
        String id,

        @Schema(description = "ID of the product in the wishlist", example = "prod_456def")
        String productId,

        @Schema(description = "Name of the product in the wishlist", example = "Bluetooth Speaker")
        String productName
) {}
