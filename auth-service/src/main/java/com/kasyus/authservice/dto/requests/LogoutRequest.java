package com.kasyus.authservice.dto.requests;

public record LogoutRequest(
        String accessToken,
        String refreshToken) {
}
