package com.codeit.async.event;

public record UserLoginFailedEvent(
        String username,
        String reason
) {
}