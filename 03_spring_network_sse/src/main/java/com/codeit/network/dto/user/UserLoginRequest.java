package com.codeit.network.dto.user;


public record UserLoginRequest(
        String username,
        String password
) {}