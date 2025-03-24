package com.kasyus.cartservice.dto.responses;

import com.kasyus.cartservice.model.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Response object representing the contents of a user's shopping cart")
public record CartResponse(

        @Schema(description = "ID of the user who owns the cart", example = "user_abc123")
        String userId,

        @Schema(description = "Total number of items in the cart", example = "3")
        int itemCount,

        @Schema(description = "List of items in the cart")
        List<CartItemResponse> items,

        @Schema(description = "Total price of all items in the cart", example = "299.97")
        BigDecimal totalPrice
) {}
