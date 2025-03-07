package com.kasyus.userservice.dto;

import java.time.LocalDateTime;

public record UserRegisteredEvent(
        String eventType,
        String userId,
        String email,
        String firstName,
        String lastName,
        String role,
        String createdBy,
        LocalDateTime timestamp
) {}