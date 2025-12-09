package com.codeit.network.dto.user;

import com.codeit.network.entity.UserRole;

import java.time.Instant;

public record MeResponse(
        Long id,
        String username,
        String nickname,
        String email,
        UserRole role,
        Instant createdAt
) {}
