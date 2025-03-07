package com.kasyus.userservice.service;

import com.kasyus.userservice.dto.UserRegisteredEvent;

public interface UserEventConsumer {
    void handleUserRegisteredEvent(UserRegisteredEvent event);

}
