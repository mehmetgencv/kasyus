package com.kasyus.cartservice.service;

import com.kasyus.cartservice.dto.requests.CartCreateRequest;
import com.kasyus.cartservice.dto.requests.CartUpdateRequest;
import com.kasyus.cartservice.dto.responses.CartResponse;

import java.math.BigDecimal;

public interface CartService {

    CartResponse getCartByUserId(String userId);
    void addItem(String userId, CartCreateRequest request);
    void updateItem(String userId, CartUpdateRequest request);
    void updateCartPrice(Long productId, BigDecimal newPrice);
    void removeItem(String userId, Long productId);
    void clearCart(String userId);
}
