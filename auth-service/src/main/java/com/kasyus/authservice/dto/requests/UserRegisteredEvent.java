package com.kasyus.authservice.dto.requests;

import java.time.LocalDateTime;

public record UserRegisteredEvent(
        String userId,
        String firstName,
        String lastName,
        String email,
        LocalDateTime registeredAt
) {
}
