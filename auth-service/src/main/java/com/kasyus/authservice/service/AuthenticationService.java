package com.kasyus.authservice.service;


import com.kasyus.authservice.dto.requests.LoginRequest;
import com.kasyus.authservice.dto.requests.RegisterRequest;
import com.kasyus.authservice.dto.responses.AuthResponse;
import com.kasyus.authservice.dto.responses.TokenValidationResponse;
import io.jsonwebtoken.Claims;

public interface AuthenticationService {

    /**
     * Registers a new user and generates JWT tokens (Access & Refresh).
     *
     * @param request The user's registration details.
     * @return AuthResponse containing the Access and Refresh tokens.
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticates the user and generates JWT tokens.
     *
     * @param request The user's login credentials.
     * @return AuthResponse containing the Access and Refresh tokens.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Logs out the user by blacklisting the provided Access and Refresh tokens.
     *
     * @param accessToken The Access Token to be invalidated.
     * @param refreshToken The Refresh Token to be invalidated.
     */
    void logout(String accessToken, String refreshToken);

    /**
     * Generates a new Access Token and Refresh Token using a valid Refresh Token.
     * The old Refresh Token is blacklisted to prevent reuse.
     *
     * @param refreshToken The Refresh Token used to request a new Access Token.
     * @return AuthResponse containing the new Access and Refresh tokens.
     */
    AuthResponse refreshToken(String refreshToken);

    TokenValidationResponse validateToken(String token);
}
