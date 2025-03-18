package com.kasyus.cartservice.dto.responses;

import com.kasyus.cartservice.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
        String userId,
        int itemCount,
        List<CartItem> items,
        BigDecimal totalPrice){
}
