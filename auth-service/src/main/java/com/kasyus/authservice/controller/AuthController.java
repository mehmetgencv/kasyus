package com.kasyus.authservice.controller;

import com.kasyus.authservice.dto.requests.LoginRequest;
import com.kasyus.authservice.dto.requests.LogoutRequest;
import com.kasyus.authservice.dto.requests.RegisterRequest;
import com.kasyus.authservice.dto.responses.AuthResponse;
import com.kasyus.authservice.dto.responses.TokenValidationResponse;
import com.kasyus.authservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Login user and receive tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @Operation(summary = "Refresh access token using refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing refresh token")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @Parameter(description = "Refresh token with Bearer prefix", example = "Bearer eyJhbGciOiJIUzI1NiIs...")
            @RequestHeader("Authorization") String refreshToken) {

        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.ok(authenticationService.refreshToken(refreshToken.substring(7)));
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Validate access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing access token")
    })
    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(
            @Parameter(description = "Access token with Bearer prefix", example = "Bearer eyJhbGciOiJIUzI1NiIs...")
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return ResponseEntity.ok(authenticationService.validateToken(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Logout user and invalidate tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User logged out successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid logout request")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Valid @RequestBody LogoutRequest request) {

        authenticationService.logout(request.accessToken(), request.refreshToken());
        return ResponseEntity.ok().build();
    }
}
