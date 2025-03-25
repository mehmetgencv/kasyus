package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.WishlistItemCreateRequest;
import com.kasyus.userservice.dto.responses.WishlistItemResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.WishlistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Add a new item to the user's wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Wishlist item added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<RestResponse<String>> addWishlistItem(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Valid @RequestBody WishlistItemCreateRequest request) {

        String wishlistItemId = wishlistItemService.addWishlistItem(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(wishlistItemId, "Wishlist item added successfully"));
    }

    @Operation(summary = "Get all wishlist items for the current user")
    @ApiResponse(responseCode = "200", description = "Wishlist items retrieved successfully")
    @GetMapping
    public ResponseEntity<RestResponse<Set<WishlistItemResponse>>> getWishlistItems(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {

        Set<WishlistItemResponse> wishlistItems = wishlistItemService.getWishlistItems(userId);
        return ResponseEntity.ok(RestResponse.of(wishlistItems, "Wishlist items retrieved successfully"));
    }

    @Operation(summary = "Delete a wishlist item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wishlist item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist item not found")
    })
    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<RestResponse<String>> deleteWishlistItem(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Parameter(description = "ID of the wishlist item to delete", example = "wish_123abc", required = true)
            @PathVariable String wishlistItemId) {

        wishlistItemService.deleteWishlistItem(userId, wishlistItemId);
        return ResponseEntity.ok(RestResponse.of(null, "Wishlist item deleted successfully"));
    }
}
