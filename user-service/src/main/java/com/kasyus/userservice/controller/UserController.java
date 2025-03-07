package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.UserProfileUpdateRequest;
import com.kasyus.userservice.dto.responses.UserProfileResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final String USER_ID_HEADER = "X-User-Id";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<RestResponse<UserProfileResponse>> getCurrentUserProfile(
            @RequestHeader(USER_ID_HEADER) String userId) {
        UserProfileResponse profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(RestResponse.of(profile, "User profile retrieved successfully"));
    }

    @PutMapping("/me/profile")
    public ResponseEntity<RestResponse<String>> updateProfile(
            @RequestHeader(USER_ID_HEADER) String userId,
            @RequestBody UserProfileUpdateRequest request) {
        userService.updateProfile(userId, request);
        return ResponseEntity.ok(RestResponse.of(userId, "Profile updated successfully"));
    }

}