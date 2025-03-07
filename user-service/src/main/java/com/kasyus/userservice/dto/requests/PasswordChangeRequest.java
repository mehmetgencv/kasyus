package com.kasyus.userservice.dto.requests;

public record PasswordChangeRequest(
        String currentPassword,
        String newPassword
) {}