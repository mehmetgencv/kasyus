package com.kasyus.userservice.swagger.address;

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
        description = "Address deleted successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        value = """
                            {
                              "data": null,
                              "responseDate": "2025-03-24T13:18:00",
                              "message": "Address deleted successfully",
                              "success": true
                            }
                        """
                )
        )
)
public @interface SuccessApiAddressDeleteResponse {
}
