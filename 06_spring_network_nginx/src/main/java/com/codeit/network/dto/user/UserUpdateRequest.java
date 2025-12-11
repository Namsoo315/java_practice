package com.codeit.network.dto.user;


public record UserUpdateRequest(
        String email,
        String password
) {}
