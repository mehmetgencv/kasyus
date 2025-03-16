package com.kasyus.userservice.service.Impl;

import com.kasyus.userservice.dto.UserRegisteredEvent;
import com.kasyus.userservice.model.User;
import com.kasyus.userservice.model.UserProfile;
import com.kasyus.userservice.model.enums.UserRole;
import com.kasyus.userservice.repository.UserRepository;
import com.kasyus.userservice.service.UserEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserEventConsumerImpl implements UserEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserEventConsumerImpl.class);
    private final UserRepository userRepository;

    public UserEventConsumerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "auth-events", groupId = "user-service-group")
    @Transactional
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        if (!"USER_REGISTERED".equals(event.eventType())) {
            logger.debug("Ignoring event type: {}", event.eventType());
            return;
        }


        if (userRepository.existsById(event.userId())) {
            logger.info("User already exists with ID: {}", event.userId());
            return;
        }

        User user = new User(
                event.userId()
        );

        UserRole userRole;
        try {
            userRole = UserRole.valueOf(event.role());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid role received: {}", event.role(), e);
            return;
        }

        UserProfile profile = new UserProfile(
                user,
                event.firstName(),
                event.lastName(),
                event.email(),
                userRole
        );
        user.setProfile(profile);

        userRepository.save(user);
        logger.info("User created from event: {}", event.userId());
    }
}
