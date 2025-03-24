package com.kasyus.cartservice.swagger;

import com.kasyus.cartservice.general.RestResponse;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponse(
        responseCode = "200",
        description = "Operation completed successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.kasyus.cartservice.general.RestResponse.class),
                examples = @ExampleObject(
                        name = "SuccessResponse",
                        summary = "Example Success Response",
                        value = """
                            {
                              "data": null,
                              "responseDate": "2025-03-24T07:32:10.024Z",
                              "message": "Operation completed successfully",
                              "success": true
                            }
                        """
                )
        )
)
public @interface SuccessApiVoidResponse {
}
