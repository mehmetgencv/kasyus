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
        responseCode = "201",
        description = "Payment method added successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        value = """
                {
                  "data": "pm_123abc",
                  "responseDate": "2025-03-24T12:30:00",
                  "message": "Payment method added successfully",
                  "success": true
                }
            """
                )
        )
)
public @interface SuccessApiPaymentMethodAddResponse {
}
