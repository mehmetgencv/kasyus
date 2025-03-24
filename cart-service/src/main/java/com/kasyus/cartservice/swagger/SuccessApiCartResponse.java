package com.kasyus.cartservice.swagger;

import com.kasyus.cartservice.dto.responses.CartResponse;
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
        description = "Cart retrieved successfully",
        content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                        name = "CartResponse",
                        summary = "Successful cart fetch",
                        value = """
                {
                  "data": {
                    "userId": "user_abc123",
                    "itemCount": 2,
                    "items": [
                      {
                        "productId": 101,
                        "quantity": 2,
                        "price": 149.99
                      }
                    ],
                    "totalPrice": 299.98
                  },
                  "responseDate": "2025-03-24T11:17:59.1702016",
                  "message": "Cart retrieved successfully",
                  "success": true
                }
            """
                )
        )
)

public @interface SuccessApiCartResponse {
}
