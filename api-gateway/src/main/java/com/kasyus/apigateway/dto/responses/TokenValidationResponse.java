package com.kasyus.apigateway.dto.responses;


import java.util.List;

public record TokenValidationResponse(
        boolean valid,
        String email,
        String userId,
        List<String> roles
) {
}
