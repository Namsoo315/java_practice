package com.codeit.cache.dto.user;

public record UserUpdateRequest(
        String username,
        String email,
        String password
) {}
