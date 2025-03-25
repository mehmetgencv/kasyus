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
        description = "Profile updated successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        name = "UserProfileUpdateSuccess",
                        summary = "Example response after successful profile update",
                        value = """
                            {
                              "data": "user_abc123",
                              "responseDate": "2025-03-24T12:00:00",
                              "message": "Profile updated successfully",
                              "success": true
                            }
                        """
                )
        )
)
public @interface SuccessApiUserProfileUpdateResponse {
}
