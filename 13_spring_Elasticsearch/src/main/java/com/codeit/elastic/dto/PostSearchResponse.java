package com.codeit.elastic.dto;

import java.time.Instant;

public record PostSearchResponse(
        Long postId,
        String title,
        String content,
        String authorUsername,
        String authorNickname,
        String category,
        Instant createdAt
) {
}
