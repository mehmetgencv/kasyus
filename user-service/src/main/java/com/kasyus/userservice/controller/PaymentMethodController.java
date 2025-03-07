package com.kasyus.userservice.controller;

import com.kasyus.userservice.dto.requests.PaymentMethodCreateRequest;
import com.kasyus.userservice.dto.requests.PaymentMethodUpdateRequest;
import com.kasyus.userservice.dto.responses.PaymentMethodResponse;
import com.kasyus.userservice.general.RestResponse;
import com.kasyus.userservice.service.PaymentMethodService;
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

    @PostMapping
    public ResponseEntity<RestResponse<String>> addPaymentMethod(
            @RequestHeader(USER_ID_HEADER) String userId,
            @RequestBody PaymentMethodCreateRequest request) {
        String paymentMethodId = paymentMethodService.addPaymentMethod(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.of(paymentMethodId, "Payment method added successfully"));
    }

    @GetMapping
    public ResponseEntity<RestResponse<Set<PaymentMethodResponse>>> getPaymentMethods(
            @RequestHeader(USER_ID_HEADER) String userId) {
        Set<PaymentMethodResponse> paymentMethods = paymentMethodService.getPaymentMethods(userId);
        return ResponseEntity.ok(RestResponse.of(paymentMethods, "Payment methods retrieved successfully"));
    }

    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<RestResponse<String>> updatePaymentMethod(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable String paymentMethodId,
            @RequestBody PaymentMethodUpdateRequest request) {
        paymentMethodService.updatePaymentMethod(userId, paymentMethodId, request);
        return ResponseEntity.ok(RestResponse.of(paymentMethodId, "Payment method updated successfully"));
    }

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<RestResponse<String>> deletePaymentMethod(
            @RequestHeader(USER_ID_HEADER) String userId,
            @PathVariable String paymentMethodId) {
        paymentMethodService.deletePaymentMethod(userId, paymentMethodId);
        return ResponseEntity.ok(RestResponse.of(null, "Payment method deleted successfully"));
    }
}