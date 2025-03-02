package com.kasyus.authservice.dto.responses;

import com.kasyus.authservice.model.User;
import com.kasyus.authservice.model.enums.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    UserRole role,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
} 