package com.codeit.cache.dto.common;


import com.codeit.cache.dto.user.UserDto;

public record LoginResponse(
        boolean success,
        String message,
        UserDto user
) {}