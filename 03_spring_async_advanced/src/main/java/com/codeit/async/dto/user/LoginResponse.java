package com.codeit.async.dto.user;

public record LoginResponse(
        Long userId,
        String username,
        String role
) {
}
