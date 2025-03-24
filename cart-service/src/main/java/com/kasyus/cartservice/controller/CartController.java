package com.kasyus.cartservice.controller;

import com.kasyus.cartservice.dto.requests.CartCreateRequest;
import com.kasyus.cartservice.dto.requests.CartUpdateRequest;
import com.kasyus.cartservice.dto.responses.CartResponse;
import com.kasyus.cartservice.general.RestResponse;
import com.kasyus.cartservice.service.CartService;
import com.kasyus.cartservice.swagger.SuccessApiCartResponse;
import com.kasyus.cartservice.swagger.SuccessApiVoidResponse;
import com.kasyus.cartservice.swagger.BadRequestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;
    private static final String USER_ID_HEADER = "X-User-Id";

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Get current user's cart")
    @SuccessApiCartResponse
    @GetMapping
    public ResponseEntity<RestResponse<CartResponse>> getCart(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {
        return ResponseEntity.ok(RestResponse.of(cartService.getCartByUserId(userId), "Cart retrieved successfully"));
    }

    @Operation(summary = "Add a new item to the cart")
    @SuccessApiVoidResponse
    @BadRequestApiResponse
    @PostMapping("/add")
    public ResponseEntity<RestResponse<Void>> addItem(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Valid @RequestBody CartCreateRequest request) {

        cartService.addItem(userId, request);
        return ResponseEntity.ok(RestResponse.of(null, "Item added to cart successfully"));
    }

    @Operation(summary = "Update quantity of an item in the cart")
    @SuccessApiVoidResponse
    @BadRequestApiResponse
    @PutMapping("/update")
    public ResponseEntity<RestResponse<Void>> updateItem(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Valid @RequestBody CartUpdateRequest request) {

        cartService.updateItem(userId, request);
        return ResponseEntity.ok(RestResponse.of(null, "Item updated in cart successfully"));
    }

    @Operation(summary = "Remove an item from the cart by product ID")
    @SuccessApiVoidResponse
    @DeleteMapping("/remove")
    public ResponseEntity<RestResponse<Void>> removeItem(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Parameter(description = "ID of the product to remove", example = "101", required = true)
            @RequestParam Long productId) {

        cartService.removeItem(userId, productId);
        return ResponseEntity.ok(RestResponse.of(null, "Item removed from cart successfully"));
    }

    @Operation(summary = "Clear all items from the cart")
    @SuccessApiVoidResponse
    @DeleteMapping("/clear")
    public ResponseEntity<RestResponse<Void>> clearCart(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {

        cartService.clearCart(userId);
        return ResponseEntity.ok(RestResponse.of(null, "Cart cleared successfully"));
    }
}
