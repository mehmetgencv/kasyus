package com.kasyus.cartservice.exception;

import java.time.LocalDateTime;

public record GeneralErrorMessages(LocalDateTime now, String message, String description) {
}
