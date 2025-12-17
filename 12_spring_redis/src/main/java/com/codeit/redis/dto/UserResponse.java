package com.codeit.redis.dto;

import java.time.Instant;

public record UserResponse(
        Long userId,
        String username,
        Instant sessionCreatedAt
) {
}

