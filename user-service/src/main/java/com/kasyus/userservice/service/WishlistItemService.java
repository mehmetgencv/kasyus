package com.kasyus.userservice.service;

import com.kasyus.userservice.dto.requests.WishlistItemCreateRequest;
import com.kasyus.userservice.dto.responses.WishlistItemResponse;

import java.util.Set;

public interface WishlistItemService {
    String addWishlistItem(String userId, WishlistItemCreateRequest request);
    Set<WishlistItemResponse> getWishlistItems(String userId);
    void deleteWishlistItem(String userId, String wishlistItemId);
}