package com.kasyus.cartservice.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Item in the user's cart")
public record CartItemResponse(

        @Schema(description = "Product ID", example = "101")
        Long productId,

        @Schema(description = "Quantity of the product", example = "2")
        int quantity,

        @Schema(description = "Unit price of the product", example = "149.99")
        BigDecimal price
) {}
