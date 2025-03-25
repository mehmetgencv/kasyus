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
        responseCode = "404",
        description = "Resource not found",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        name = "GenericNotFoundExample",
                        summary = "Generic not found response",
                        value = """
                            {
                              "data": null,
                              "responseDate": "2025-03-24T11:55:00",
                              "message": "Resource not found with the given identifier",
                              "success": false
                            }
                        """
                )
        )
)
public @interface NotFoundApiResponse {
}
