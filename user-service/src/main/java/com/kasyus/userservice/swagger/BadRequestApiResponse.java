package com.kasyus.userservice.swagger;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponse(
        responseCode = "400",
        description = "Invalid input",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.kasyus.userservice.general.RestResponse.class),
                examples = @ExampleObject(
                        name = "ValidationError",
                        summary = "Example validation error",
                        value = """
                            {
                              "data": null,
                              "responseDate": "2025-03-24T07:32:10.024Z",
                              "message": "Validation failed",
                              "success": false
                            }
                        """
                )
        )
)
public @interface BadRequestApiResponse {
}
