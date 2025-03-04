package com.kasyus.authservice.service.impl;

import com.kasyus.authservice.model.User;
import com.kasyus.authservice.service.AuthEventPublisher;
import com.kasyus.authservice.service.OutboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthEventPublisherImpl implements AuthEventPublisher {

    private final OutboxService outboxService;
    private static final String AGGREGATE_TYPE = "auth";

    public AuthEventPublisherImpl(OutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @Override
    @Transactional
    public void publishUserRegistered(User user) {
        publishAuthEvent("USER_REGISTERED", user);
    }

    @Override
    @Transactional
    public void publishUserLoggedIn(User user) {
        publishAuthEvent("USER_LOGGED_IN", user);
    }

    @Override
    @Transactional
    public void publishUserLoggedOut(User user) {
        publishAuthEvent("USER_LOGGED_OUT", user);
    }

    private void publishAuthEvent(String eventType, User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("eventType", eventType);
        payload.put("userId", user.getId().toString());
        payload.put("firstName", user.getFirstName());
        payload.put("lastName", user.getLastName());
        payload.put("email", user.getEmail());
        payload.put("timestamp", LocalDateTime.now());
        payload.put("role", user.getRole().toString());
        payload.put("createdBy", user.getEmail());

        outboxService.saveEvent(
            AGGREGATE_TYPE,
            user.getId().toString(),
            eventType,
            payload
        );
    }
} 