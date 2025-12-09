package com.codeit.network.dto.user;

import com.codeit.network.entity.UserRole;

public record UserLoginResponse(
        Long id,
        String username,
        String nickname,
        String email,
        UserRole role
) {}
