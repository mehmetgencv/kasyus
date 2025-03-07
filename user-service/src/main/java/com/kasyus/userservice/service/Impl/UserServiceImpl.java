package com.kasyus.userservice.service.Impl;

import com.kasyus.userservice.dto.requests.UserProfileUpdateRequest;
import com.kasyus.userservice.dto.responses.UserProfileResponse;
import com.kasyus.userservice.exception.UserNotFoundException;
import com.kasyus.userservice.mapper.UserMapper;
import com.kasyus.userservice.model.User;
import com.kasyus.userservice.model.UserProfile;
import com.kasyus.userservice.repository.UserRepository;
import com.kasyus.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserProfileResponse getUserProfile(String userId) {
        User user = getUserEntity(userId);
        UserProfile profile = user.getProfile();
        if (profile == null) {
            throw new IllegalStateException("User profile not found for user: " + userId);
        }
        return userMapper.toUserProfileResponse(profile);
    }

    @Override
    @Transactional
    public void updateProfile(String userId, UserProfileUpdateRequest request) {
        User user = getUserEntity(userId);
        UserProfile profile = user.getProfile();
        if (profile == null) {
            profile = new UserProfile(user, request.firstName(), request.lastName(), request.email());
            user.setProfile(profile);
        } else {
            profile.setFirstName(request.firstName());
            profile.setLastName(request.lastName());
            profile.setEmail(request.email());
            profile.setPhoneNumber(request.phoneNumber());
            profile.setDateOfBirth(request.dateOfBirth());
            profile.setCustomerSegment(request.customerSegment());
            profile.setLoyaltyPoints(request.loyaltyPoints());
        }
        userRepository.save(user);
        logger.info("Profile updated for user: {}", userId);
    }

    private User getUserEntity(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}