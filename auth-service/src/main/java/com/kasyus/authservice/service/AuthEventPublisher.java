package com.kasyus.authservice.service;

import com.kasyus.authservice.model.User;

public interface AuthEventPublisher {

    /**
     * Publishes an event when a user registers.
     *
     * @param user The registered user.
     */
    void publishUserRegistered(User user);

    /**
     * Publishes an event when a user logs in.
     *
     * @param user The authenticated user.
     */
    void publishUserLoggedIn(User user);

    /**
     * Publishes an event when a user logs out.
     *
     * @param user The user who logged out.
     */
    void publishUserLoggedOut(User user);
}
