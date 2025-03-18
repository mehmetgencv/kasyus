package com.kasyus.cartservice.controller;

import com.kasyus.cartservice.dto.requests.CartCreateRequest;
import com.kasyus.cartservice.dto.requests.CartUpdateRequest;
import com.kasyus.cartservice.dto.responses.CartResponse;
import com.kasyus.cartservice.general.RestResponse;
import com.kasyus.cartservice.service.CartService;
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

    @GetMapping
    public ResponseEntity<RestResponse<CartResponse>> getCart(@RequestHeader(USER_ID_HEADER) String userId) {
        return ResponseEntity.ok(RestResponse.of(cartService.getCartByUserId(userId), "Cart retrieved successfully"));
    }

    @PostMapping("/add")
    public ResponseEntity<RestResponse<Void>> addItem(@RequestHeader(USER_ID_HEADER) String userId, @RequestBody CartCreateRequest request) {
        cartService.addItem(userId, request);
        return ResponseEntity.ok(RestResponse.of(null, "Item added to cart successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<RestResponse<Void>> updateItem(@RequestHeader(USER_ID_HEADER) String userId, @RequestBody CartUpdateRequest request) {
        cartService.updateItem(userId, request);
        return ResponseEntity.ok(RestResponse.of(null, "Item updated in cart successfully"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<RestResponse<Void>> removeItem(@RequestHeader(USER_ID_HEADER) String userId, @RequestParam Long productId) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.ok(RestResponse.of(null, "Item removed from cart successfully"));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<RestResponse<Void>> clearCart(@RequestHeader(USER_ID_HEADER) String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(RestResponse.of(null, "Cart cleared successfully"));
    }
}