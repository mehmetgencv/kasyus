package com.kasyus.authservice.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    /**
     * Extracts the username (subject) from a given JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    String extractUsername(String token);

    /**
     * Generates a new access token for the given user.
     *
     * @param userDetails The user details for whom the token is generated.
     * @return A newly generated JWT access token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a new access token with additional claims.
     *
     * @param extraClaims Additional claims to be added to the token.
     * @param userDetails The user details for whom the token is generated.
     * @return A newly generated JWT access token with extra claims.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Generates a refresh token for the given user.
     *
     * @param userDetails The user details for whom the refresh token is generated.
     * @return A newly generated JWT refresh token.
     */
    String generateRefreshToken(UserDetails userDetails);

    /**
     * Validates a JWT token by checking its expiration and matching the user details.
     *
     * @param token The JWT token to validate.
     * @param userDetails The user details to verify against the token.
     * @return True if the token is valid, false otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
