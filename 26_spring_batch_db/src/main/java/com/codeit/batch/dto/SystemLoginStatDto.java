package com.codeit.batch.dto;

import java.time.Instant;

public record SystemLoginStatDto(
        long totalUsers,
        long totalLoginAttempts,
        long totalSuccessLogins,
        Instant lastLoginTime
) {}
