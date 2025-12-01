package com.codeit.cache.dto.user;

public record UserCreateRequest(
        String username,
        String email,
        String password
) {}