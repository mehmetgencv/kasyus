package com.kasyus.orderservice.functions;

import com.kasyus.orderservice.service.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderFunctions {

    private static final Logger log = LoggerFactory.getLogger(OrderFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(OrderServiceImpl orderService) {
        return orderId -> {
            log.info("Updating Communication status for the order ID: {}", orderId.toString());
        orderService.updateCommunicationStatus(orderId);
        };
    }
}
