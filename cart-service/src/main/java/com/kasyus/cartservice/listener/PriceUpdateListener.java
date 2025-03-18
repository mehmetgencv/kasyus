package com.kasyus.cartservice.listener;

import com.kasyus.cartservice.dto.PriceUpdatedEvent;
import com.kasyus.cartservice.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PriceUpdateListener {

    private static final Logger log = LoggerFactory.getLogger(PriceUpdateListener.class);
    private final CartService cartService;

    public PriceUpdateListener(CartService cartService) {
        this.cartService = cartService;
    }

    @KafkaListener(topics = "product-price-updated", groupId = "cart-service-price-update-group")
    public void handlePriceUpdate(PriceUpdatedEvent event) {
        log.info("Received price update event: productId={}, newPrice={}", event.productId(), event.newPrice());
        cartService.updateCartPrice(event.productId(), event.newPrice()); // Service’e devret
    }
}