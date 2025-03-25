package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.UserProfileUpdateRequest;
import com.kasyus.userservice.dto.responses.UserProfileResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.UserService;
import com.kasyus.userservice.swagger.BadRequestApiResponse;
import com.kasyus.userservice.swagger.NotFoundApiResponse;
import com.kasyus.userservice.swagger.SuccessApiUserProfileResponse;
import com.kasyus.userservice.swagger.SuccessApiUserProfileUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(
            summary = "Get current user's profile",
            description = "Retrieves the authenticated user's profile based on the X-User-Id header."
    )
    @SuccessApiUserProfileResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @GetMapping("/me")
    public ResponseEntity<RestResponse<UserProfileResponse>> getCurrentUserProfile(
            @RequestHeader(USER_ID_HEADER) String userId) {
        UserProfileResponse profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(RestResponse.of(profile, "User profile retrieved successfully"));
    }

    @Operation(
            summary = "Update current user's profile",
            description = "Updates the authenticated user's profile with provided data."
    )
    @SuccessApiUserProfileUpdateResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @PutMapping("/me/profile")
    public ResponseEntity<RestResponse<String>> updateProfile(
            @RequestHeader(USER_ID_HEADER) String userId,
            @Valid @RequestBody UserProfileUpdateRequest request) {
        userService.updateProfile(userId, request);
        return ResponseEntity.ok(RestResponse.of(userId, "Profile updated successfully"));
    }

}