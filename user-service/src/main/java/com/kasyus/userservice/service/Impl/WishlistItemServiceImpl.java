package com.kasyus.userservice.service.Impl;

import com.kasyus.userservice.dto.requests.WishlistItemCreateRequest;
import com.kasyus.userservice.dto.responses.WishlistItemResponse;
import com.kasyus.userservice.exception.UserNotFoundException;
import com.kasyus.userservice.mapper.WishlistItemMapper;
import com.kasyus.userservice.model.User;
import com.kasyus.userservice.model.WishlistItem;
import com.kasyus.userservice.repository.UserRepository;
import com.kasyus.userservice.service.WishlistItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WishlistItemServiceImpl implements WishlistItemService {

    private static final Logger logger = LoggerFactory.getLogger(WishlistItemServiceImpl.class);
    private final UserRepository userRepository;
    private final WishlistItemMapper wishlistItemMapper;

    public WishlistItemServiceImpl(UserRepository userRepository, WishlistItemMapper wishlistItemMapper) {
        this.userRepository = userRepository;
        this.wishlistItemMapper = wishlistItemMapper;
    }

    @Override
    @Transactional
    public String addWishlistItem(String userId, WishlistItemCreateRequest request) {
        User user = getUserEntity(userId);
        WishlistItem wishlistItem = wishlistItemMapper.toWishlistItem(request);
        wishlistItem.setUser(user);
        user.getWishlistItems().add(wishlistItem);
        userRepository.save(user);
        logger.info("Wishlist item added to user: {}", userId);
        return wishlistItem.getId().toString();
    }

    @Override
    public Set<WishlistItemResponse> getWishlistItems(String userId) {
        User user = getUserEntity(userId);
        return user.getWishlistItems().stream()
                .map(wishlistItemMapper::toWishlistItemResponse)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void deleteWishlistItem(String userId, String wishlistItemId) {
        User user = getUserEntity(userId);
        WishlistItem wishlistItem = user.getWishlistItems().stream()
                .filter(wi -> wi.getId().toString().equals(wishlistItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Wishlist item not found: " + wishlistItemId));
        user.getWishlistItems().remove(wishlistItem);
        userRepository.save(user);
        logger.info("Wishlist item deleted for user: {}, wishlistItemId: {}", userId, wishlistItemId);
    }

    private User getUserEntity(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}