package com.codeit.async.event;

public record UserLoginSuccessEvent(
        Long userId,
        String username
) {
}