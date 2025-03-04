package com.kasyus.authservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasyus.authservice.model.OutboxEvent;
import com.kasyus.authservice.repository.OutboxEventRepository;
import com.kasyus.authservice.service.OutboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
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
    @Scheduled(fixedRate = 5000)
    @Transactional
    public void processOutboxEvents() {
        var events = outboxEventRepository.findUnpublishedEventsWithLock(PageRequest.of(0, 100));

        for (OutboxEvent event : events) {
            try {
                Map<String, Object> payload = objectMapper.readValue(event.getPayload(), MAP_TYPE_REFERENCE);

                kafkaTemplate.send(
                        event.getAggregateType() + "-events",
                        event.getAggregateId(),
                        payload
                ).whenComplete((result, ex) -> {
                    if (ex == null) {
                        markEventAsPublished(event);
                        logger.info("Successfully published event: {}", event.getId());
                    } else {
                        handleRetryOrDLQ(event, ex);
                    }
                });
            } catch (Exception e) {
                logger.error("Failed to deserialize payload for event: {}", event.getId(), e);
                handleRetryOrDLQ(event, e);
            }
        }
    }

    private void handleRetryOrDLQ(OutboxEvent event, Throwable ex) {
        event.incrementRetryCount();
        if (event.shouldRetry()) {
            logger.warn("Retrying event: {}. Retry count: {}", event.getId(), event.getRetryCount());
            outboxEventRepository.save(event); // Tekrar denensin diye published=false kalır
        } else {
            logger.error("Max retries reached for event: {}. Sending to DLQ", event.getId(), ex);
            kafkaTemplate.send(
                    event.getAggregateType() + "-events-dlq",
                    event.getAggregateId(),
                    objectMapper.convertValue(event.getPayload(), MAP_TYPE_REFERENCE)
            ).whenComplete((dlqResult, dlqEx) -> {
                if (dlqEx == null) {
                    logger.info("Event sent to DLQ: {}", event.getId());
                    markEventAsPublished(event);
                } else {
                    logger.error("Failed to send event to DLQ: {}", event.getId(), dlqEx);
                }
            });
        }
    }

    private void markEventAsPublished(OutboxEvent event) {
        event.markAsPublished();
        outboxEventRepository.save(event);
    }
} 