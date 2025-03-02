package com.kasyus.authservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasyus.authservice.model.OutboxEvent;
import com.kasyus.authservice.repository.OutboxEventRepository;
import com.kasyus.authservice.service.OutboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class OutboxServiceImpl implements OutboxService {
    private static final Logger logger = LoggerFactory.getLogger(OutboxServiceImpl.class);
    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = 
        new TypeReference<>() {};

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OutboxServiceImpl(
            OutboxEventRepository outboxEventRepository,
            KafkaTemplate<String, Object> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void saveEvent(String aggregateType, String aggregateId, String eventType, Map<String, Object> payload) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            OutboxEvent event = new OutboxEvent(aggregateType, aggregateId, eventType, jsonPayload);
            outboxEventRepository.save(event);
        } catch (Exception e) {
            logger.error("Failed to save outbox event", e);
            throw new RuntimeException("Failed to save outbox event", e);
        }
    }

    @Override
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    @Transactional
    public void processOutboxEvents() {
        var events = outboxEventRepository.findUnpublishedEventsWithLock();
        
        for (OutboxEvent event : events) {
            try {
                // Deserialize JSON payload with explicit type information
                Map<String, Object> payload = objectMapper.readValue(event.getPayload(), MAP_TYPE_REFERENCE);
                
                // Publish to Kafka
                kafkaTemplate.send(
                    event.getAggregateType() + "-events",
                    event.getAggregateId(),
                    payload
                ).get(); // Wait for the send to complete

                // Mark as published
                event.markAsPublished();
                outboxEventRepository.save(event);
                
                logger.info("Successfully published event: {}", event.getId());
            } catch (Exception e) {
                logger.error("Failed to process outbox event: {}", event.getId(), e);
                // Don't throw the exception - let other events be processed
            }
        }
    }
} 