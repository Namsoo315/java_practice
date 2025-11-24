package com.codeit.jwt.dto.user;


public record UserUpdateRequest(
        String email,
        String password
) {}
