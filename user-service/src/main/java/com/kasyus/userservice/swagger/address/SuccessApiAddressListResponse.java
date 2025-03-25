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
        description = "Addresses retrieved successfully",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Object.class),
                examples = @ExampleObject(
                        name = "AddressListExample",
                        summary = "Successful address list retrieval",
                        value = """
                            {
                              "data": [
                                {
                                  "id": "f3d9a1f5-2c8b-4e99-9a13-42e72f9849ab",
                                  "type": "SHIPPING",
                                  "name": "Home",
                                  "isDefault": true,
                                  "streetAddress": "123 Main Street",
                                  "city": "Istanbul",
                                  "state": "Kadikoy",
                                  "postalCode": "34742",
                                  "country": "Turkey",
                                  "phone": "+905551234567"
                                },
                                {
                                  "id": "f4a1b2c3-1d2e-4f56-8f00-a1b2c3d4e5f6",
                                  "type": "BILLING",
                                  "name": "Office",
                                  "isDefault": false,
                                  "streetAddress": "456 Work Ave",
                                  "city": "Ankara",
                                  "state": "Cankaya",
                                  "postalCode": "06420",
                                  "country": "Turkey",
                                  "phone": "+905551112233"
                                }
                              ],
                              "responseDate": "2025-03-24T13:20:00",
                              "message": "Addresses retrieved successfully",
                              "success": true
                            }
                        """
                )
        )
)
public @interface SuccessApiAddressListResponse {
}
