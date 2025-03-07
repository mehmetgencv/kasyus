package com.kasyus.userservice.service.Impl;


import com.kasyus.userservice.dto.requests.PaymentMethodCreateRequest;
import com.kasyus.userservice.dto.requests.PaymentMethodUpdateRequest;
import com.kasyus.userservice.dto.responses.PaymentMethodResponse;
import com.kasyus.userservice.exception.UserNotFoundException;
import com.kasyus.userservice.mapper.PaymentMethodMapper;
import com.kasyus.userservice.model.PaymentMethod;
import com.kasyus.userservice.model.User;
import com.kasyus.userservice.repository.UserRepository;
import com.kasyus.userservice.service.PaymentMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodServiceImpl.class);
    private final UserRepository userRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    public PaymentMethodServiceImpl(UserRepository userRepository, PaymentMethodMapper paymentMethodMapper) {
        this.userRepository = userRepository;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    @Override
    @Transactional
    public String addPaymentMethod(String userId, PaymentMethodCreateRequest request) {
        User user = getUserEntity(userId);
        PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(request);
        paymentMethod.setUser(user);
        user.getPaymentMethods().add(paymentMethod);
        userRepository.save(user);
        logger.info("Payment method added to user: {}", userId);
        return paymentMethod.getId().toString();
    }

    @Override
    public Set<PaymentMethodResponse> getPaymentMethods(String userId) {
        User user = getUserEntity(userId);
        return user.getPaymentMethods().stream()
                .map(paymentMethodMapper::toPaymentMethodResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void updatePaymentMethod(String userId, String paymentMethodId, PaymentMethodUpdateRequest request) {
        User user = getUserEntity(userId);
        PaymentMethod paymentMethod = user.getPaymentMethods().stream()
                .filter(pm -> pm.getId().toString().equals(paymentMethodId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Payment method not found: " + paymentMethodId));
        paymentMethod.setType(request.type());
        paymentMethod.setDefault(request.isDefault());
        paymentMethod.setProvider(request.provider());
        paymentMethod.setToken(request.token());
        paymentMethod.setLastFour(request.lastFour());
        paymentMethod.setExpiryDate(request.expiryDate());
        userRepository.save(user);
        logger.info("Payment method updated for user: {}, paymentMethodId: {}", userId, paymentMethodId);
    }

    @Override
    @Transactional
    public void deletePaymentMethod(String userId, String paymentMethodId) {
        User user = getUserEntity(userId);
        PaymentMethod paymentMethod = user.getPaymentMethods().stream()
                .filter(pm -> pm.getId().toString().equals(paymentMethodId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Payment method not found: " + paymentMethodId));
        user.getPaymentMethods().remove(paymentMethod);
        userRepository.save(user);
        logger.info("Payment method deleted for user: {}, paymentMethodId: {}", userId, paymentMethodId);
    }

    private User getUserEntity(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}