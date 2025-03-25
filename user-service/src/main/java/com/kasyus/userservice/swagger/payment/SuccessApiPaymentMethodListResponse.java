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
        description = "Payment methods retrieved successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        value = """
                {
                  "data": [
                    {
                      "id": "pm_123abc",
                      "type": "CREDIT_CARD",
                      "lastFourDigits": "1234",
                      "isDefault": true
                    },
                    {
                      "id": "pm_456def",
                      "type": "PAYPAL",
                      "lastFourDigits": "N/A",
                      "isDefault": false
                    }
                  ],
                  "responseDate": "2025-03-24T12:35:00",
                  "message": "Payment methods retrieved successfully",
                  "success": true
                }
            """
                )
        )
)
public @interface SuccessApiPaymentMethodListResponse {
}
