package com.codeit.async.event;

public record UserRegisteredEvent(
        Long userId,
        String username,
        String email,
        String role
) {
}
