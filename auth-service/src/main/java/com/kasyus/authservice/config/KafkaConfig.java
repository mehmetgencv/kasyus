package com.kasyus.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic authEventsTopic() {
        return TopicBuilder.name("auth-events")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic authEventsDlqTopic() {
        return new NewTopic("auth-events-dlq", 1, (short) 1);
    }
} 