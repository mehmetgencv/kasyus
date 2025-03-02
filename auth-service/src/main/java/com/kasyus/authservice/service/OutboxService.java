package com.kasyus.authservice.service;


import java.util.Map;

public interface OutboxService {
    void saveEvent(String aggregateType, String aggregateId, String eventType, Map<String, Object> payload);
    void processOutboxEvents();
}