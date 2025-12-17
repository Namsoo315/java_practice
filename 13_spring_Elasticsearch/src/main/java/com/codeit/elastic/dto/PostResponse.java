package com.codeit.elastic.dto;

import java.time.Instant;

public record PostResponse(
        Long postId,
        String title,
        String content,
        String tags,
        String category,
        Long authorId,
        String authorUsername,
        String authorNickname,
        Instant createdAt,
        Instant updatedAt,
        boolean deleted
) {}
