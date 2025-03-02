package com.kasyus.authservice.dto.responses;

import com.kasyus.authservice.model.enums.UserRole;

import java.util.List;

public record TokenValidationResponse(
        boolean valid,
        String email,
        List<UserRole> roles
) {
}
