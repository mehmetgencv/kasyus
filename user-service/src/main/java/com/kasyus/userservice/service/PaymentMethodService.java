package com.kasyus.userservice.service;

import com.kasyus.userservice.dto.requests.PaymentMethodCreateRequest;
import com.kasyus.userservice.dto.requests.PaymentMethodUpdateRequest;
import com.kasyus.userservice.dto.responses.PaymentMethodResponse;

import java.util.Set;

public interface PaymentMethodService {
    String addPaymentMethod(String userId, PaymentMethodCreateRequest request);
    Set<PaymentMethodResponse> getPaymentMethods(String userId);
    void updatePaymentMethod(String userId, String paymentMethodId, PaymentMethodUpdateRequest request);
    void deletePaymentMethod(String userId, String paymentMethodId);
}