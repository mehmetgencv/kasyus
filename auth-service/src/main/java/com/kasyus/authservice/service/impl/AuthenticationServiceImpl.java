package com.kasyus.authservice.service.impl;


import com.kasyus.authservice.dto.requests.LoginRequest;
import com.kasyus.authservice.dto.requests.RegisterRequest;
import com.kasyus.authservice.dto.responses.AuthResponse;
import com.kasyus.authservice.dto.responses.TokenValidationResponse;
import com.kasyus.authservice.model.User;
import com.kasyus.authservice.repository.UserRepository;
import com.kasyus.authservice.security.JwtService;
import com.kasyus.authservice.service.AuthEventPublisher;
import com.kasyus.authservice.service.AuthenticationService;
import com.kasyus.authservice.service.TokenBlacklistService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;
    private final AuthEventPublisher authEventPublisher;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            TokenBlacklistService tokenBlacklistService,
            AuthEventPublisher authEventPublisher
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenBlacklistService = tokenBlacklistService;
        this.authEventPublisher = authEventPublisher;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        var user = new User(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.role()
        );

        userRepository.save(user);
        
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        authEventPublisher.publishUserRegistered(user);
        
        return AuthResponse.of(accessToken, refreshToken);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        authEventPublisher.publishUserLoggedIn(user);
        
        return AuthResponse.of(accessToken, refreshToken);
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        tokenBlacklistService.blacklistToken(accessToken);
        tokenBlacklistService.blacklistToken(refreshToken);

        var username = jwtService.extractUsername(accessToken);
        userRepository.findByEmail(username).ifPresent(authEventPublisher::publishUserLoggedOut);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user) || tokenBlacklistService.isTokenBlacklisted(refreshToken)) {
            throw new IllegalArgumentException("Invalid or blacklisted refresh token");
        }

        tokenBlacklistService.blacklistToken(refreshToken);

        var newAccessToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse.of(newAccessToken, newRefreshToken);
    }

    @Override
    public TokenValidationResponse validateToken(String token) {
        final String userEmail = jwtService.extractUsername(token);
        if (userEmail == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!jwtService.isTokenValid(token, user) || tokenBlacklistService.isTokenBlacklisted(token)) {
            throw new IllegalArgumentException("Invalid or blacklisted refresh token");
        }

        return new TokenValidationResponse(
                jwtService.isTokenValid(token, user),
                user.getEmail(),
                user.getId().toString(),
                Collections.singletonList(user.getRole())
        );

    }


} 