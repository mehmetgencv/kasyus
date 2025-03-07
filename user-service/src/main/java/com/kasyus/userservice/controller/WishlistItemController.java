package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.WishlistItemCreateRequest;
import com.kasyus.userservice.dto.responses.WishlistItemResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.WishlistItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users/me/wishlist-items")
public class WishlistItemController {

    private static final String USER_ID_HEADER = "X-User-Id";
    private final WishlistItemService wishlistItemService;

    public WishlistItemController(WishlistItemService wishlistItemService) {
        this.wishlistItemService = wishlistItemService;
    }

    @PostMapping
    public ResponseEntity<RestResponse<String>> addWishlistItem(
            @RequestHeader(USER_ID_HEADER) String userId,
            @RequestBody WishlistItemCreateRequest request) {
        String wishlistItemId = wishlistItemService.addWishlistItem(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(wishlistItemId, "Wishlist item added successfully"));
    }

    @GetMapping
    public ResponseEntity<RestResponse<Set<WishlistItemResponse>>> getWishlistItems(
            @RequestHeader(USER_ID_HEADER) String userId) {
        Set<WishlistItemResponse> wishlistItems = wishlistItemService.getWishlistItems(userId);
        return ResponseEntity.ok(RestResponse.of(wishlistItems, "Wishlist items retrieved successfully"));
    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<RestResponse<String>> deleteWishlistItem(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable String wishlistItemId) {
        wishlistItemService.deleteWishlistItem(userId, wishlistItemId);
        return ResponseEntity.ok(RestResponse.of(null, "Wishlist item deleted successfully"));
    }
}