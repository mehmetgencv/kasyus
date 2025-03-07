package com.kasyus.userservice.service;

import com.kasyus.userservice.dto.requests.UserProfileUpdateRequest;
import com.kasyus.userservice.dto.responses.UserProfileResponse;

public interface UserService {
    UserProfileResponse getUserProfile(String userId);
    void updateProfile(String userId, UserProfileUpdateRequest request);
} 