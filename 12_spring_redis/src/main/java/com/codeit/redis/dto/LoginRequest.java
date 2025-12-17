package com.codeit.redis.dto;

public record LoginRequest(
        String username,
        String password
) {
}
