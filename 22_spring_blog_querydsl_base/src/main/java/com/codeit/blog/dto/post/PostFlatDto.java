package com.codeit.blog.dto.post;

import java.time.Instant;

public record PostFlatDto(
        Long id,
        String title,
        Instant createdAt,
        Long commentCount,
        String authorUsername,
        String authorNickname
) { }
