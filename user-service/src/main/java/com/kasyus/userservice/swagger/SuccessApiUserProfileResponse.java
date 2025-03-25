package com.kasyus.userservice.swagger;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponse(
        responseCode = "200",
        description = "User profile retrieved successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        name = "UserProfileResponseExample",
                        summary = "Example response for user profile",
                        value = """
                            {
                              "data": {
                                "id": "user_123456",
                                "firstName": "Mehmet",
                                "lastName": "Genç",
                                "email": "mehmet@example.com",
                                "phoneNumber": "+905551234567",
                                "dateOfBirth": "1995-08-15",
                                "customerSegment": "Premium",
                                "loyaltyPoints": 120,
                                "role": "USER"
                              },
                              "responseDate": "2025-03-24T11:45:00",
                              "message": "User profile retrieved successfully",
                              "success": true
                            }
                        """
                )
        )
)
public @interface SuccessApiUserProfileResponse {
}
