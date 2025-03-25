package com.kasyus.authservice.dto.responses;

import com.kasyus.authservice.model.User;
import com.kasyus.authservice.model.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response object representing a user")
public record UserResponse(

        @Schema(description = "Unique identifier of the user", example = "1")
        Long id,

        @Schema(description = "User's first name", example = "Mehmet")
        String firstName,

        @Schema(description = "User's last name", example = "Genç")
        String lastName,

        @Schema(description = "User's email address", example = "mehmet@example.com")
        String email,

        @Schema(description = "Role assigned to the user", example = "USER")
        UserRole role
) {

}
