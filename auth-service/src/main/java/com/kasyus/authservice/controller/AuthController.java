package com.kasyus.authservice.controller;


import com.kasyus.authservice.dto.requests.LoginRequest;
import com.kasyus.authservice.dto.requests.LogoutRequest;
import com.kasyus.authservice.dto.requests.RegisterRequest;
import com.kasyus.authservice.dto.responses.AuthResponse;
import com.kasyus.authservice.dto.responses.TokenValidationResponse;
import com.kasyus.authservice.service.AuthenticationService;
import io.jsonwebtoken.Claims;
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

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.ok(authenticationService.refreshToken(refreshToken.substring(7)));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return ResponseEntity.ok(authenticationService.validateToken(token));
        }
            return ResponseEntity.badRequest().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        if (request.accessToken() != null && request.refreshToken() != null) {
            authenticationService.logout(request.accessToken(), request.refreshToken());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}