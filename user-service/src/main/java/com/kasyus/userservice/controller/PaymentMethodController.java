package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.PaymentMethodCreateRequest;
import com.kasyus.userservice.dto.requests.PaymentMethodUpdateRequest;
import com.kasyus.userservice.dto.responses.PaymentMethodResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.PaymentMethodService;
import com.kasyus.userservice.swagger.*;
import com.kasyus.userservice.swagger.payment.SuccessApiPaymentMethodAddResponse;
import com.kasyus.userservice.swagger.payment.SuccessApiPaymentMethodListResponse;
import com.kasyus.userservice.swagger.payment.SuccessApiPaymentMethodUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users/me/payment-methods")
public class PaymentMethodController {

    private static final String USER_ID_HEADER = "X-User-Id";
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @Operation(summary = "Add a new payment method for the current user")
    @SuccessApiPaymentMethodAddResponse
    @BadRequestApiResponse
    @PostMapping
    public ResponseEntity<RestResponse<String>> addPaymentMethod(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Valid @RequestBody PaymentMethodCreateRequest request) {

        String paymentMethodId = paymentMethodService.addPaymentMethod(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(paymentMethodId, "Payment method added successfully"));
    }

    @Operation(summary = "Get all payment methods for the current user")
    @SuccessApiPaymentMethodListResponse
    @GetMapping
    public ResponseEntity<RestResponse<Set<PaymentMethodResponse>>> getPaymentMethods(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {

        Set<PaymentMethodResponse> paymentMethods = paymentMethodService.getPaymentMethods(userId);
        return ResponseEntity.ok(RestResponse.of(paymentMethods, "Payment methods retrieved successfully"));
    }

    @Operation(summary = "Update an existing payment method")
    @SuccessApiPaymentMethodUpdateResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<RestResponse<String>> updatePaymentMethod(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Parameter(description = "ID of the payment method to update", example = "pm_123abc", required = true)
            @PathVariable String paymentMethodId,

            @Valid @RequestBody PaymentMethodUpdateRequest request) {

        paymentMethodService.updatePaymentMethod(userId, paymentMethodId, request);
        return ResponseEntity.ok(RestResponse.of(paymentMethodId, "Payment method updated successfully"));
    }

    @Operation(summary = "Delete a payment method")
    @SuccessApiPaymentMethodUpdateResponse
    @BadRequestApiResponse
    @NotFoundApiResponse
    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<RestResponse<String>> deletePaymentMethod(
            @Parameter(description = "Authenticated user's ID", required = true)
            @RequestHeader(USER_ID_HEADER) String userId,

            @Parameter(description = "ID of the payment method to delete", example = "pm_123abc", required = true)
            @PathVariable String paymentMethodId) {

        paymentMethodService.deletePaymentMethod(userId, paymentMethodId);
        return ResponseEntity.ok(RestResponse.of(null, "Payment method deleted successfully"));
    }
}
