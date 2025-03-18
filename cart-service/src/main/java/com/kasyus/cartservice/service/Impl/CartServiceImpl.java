package com.kasyus.cartservice.service.Impl;

import com.kasyus.cartservice.service.CartService;
import com.kasyus.cartservice.dto.requests.CartCreateRequest;
import com.kasyus.cartservice.dto.requests.CartUpdateRequest;
import com.kasyus.cartservice.dto.responses.CartResponse;
import com.kasyus.cartservice.model.Cart;
import com.kasyus.cartservice.model.CartItem;
import com.kasyus.cartservice.mapper.CartMapper;
import com.kasyus.cartservice.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public CartResponse getCartByUserId(String userId) {
        Cart cart = getOrCreateCart(userId);
        return CartMapper.INSTANCE.toCartResponse(cart);
    }

    @Override
    @Transactional
    public void addItem(String userId, CartCreateRequest request) {
        Cart cart = getOrCreateCart(userId);
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.productId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
            item.setPrice(request.price());
        } else {

            CartItem item = CartMapper.INSTANCE.toCartItem(request, cart);
            cart.getItems().add(item);
        }
        cart.recalculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void updateItem(String userId, CartUpdateRequest request) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.productId()))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(request.quantity());
                    cart.recalculateTotalPrice();
                    cartRepository.save(cart);
                });
    }

    @Override
    @Transactional
    public void updateCartPrice(Long productId, BigDecimal newPrice) {
        List<Cart> carts = cartRepository.findAll().stream()
                .filter(cart -> cart.getItems().stream()
                        .anyMatch(item -> item.getProductId().equals(productId)))
                .toList();

        for (Cart cart : carts) {
            boolean updated = false;
            for (CartItem item : cart.getItems()) {
                if (item.getProductId().equals(productId)) {
                    item.setPrice(newPrice);
                    updated = true;
                }
            }
            if (updated) {
                cart.recalculateTotalPrice();
                cartRepository.save(cart);
            }
        }
    }

    @Override
    @Transactional
    public void removeItem(String userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cart.recalculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(String userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cart.recalculateTotalPrice();
        cartRepository.save(cart);
    }


    private Cart getOrCreateCart(String userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        return cart.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return newCart; //cartRepository.save(newCart);
        });
    }
}