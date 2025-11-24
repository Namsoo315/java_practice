package com.codeit.jwt.dto.user;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email,
        String role,
        Instant createdAt,
        Instant updatedAt
) {}