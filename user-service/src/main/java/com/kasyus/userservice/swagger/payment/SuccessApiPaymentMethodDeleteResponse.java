package com.kasyus.userservice.swagger.payment;

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
        description = "Payment method deleted successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        value = """
                {
                  "data": null,
                  "responseDate": "2025-03-24T12:45:00",
                  "message": "Payment method deleted successfully",
                  "success": true
                }
            """
                )
        )
)
public @interface SuccessApiPaymentMethodDeleteResponse {
}
