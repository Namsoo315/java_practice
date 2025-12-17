package com.codeit.elastic.dto;

import lombok.Builder;

@Builder
public record PostSearchRow(
        Long postId,
        String title,
        String content,
        String tags,
        String category,
        Long authorId,
        String authorUsername,
        String authorNickname,
        java.time.Instant createdAt
) {}
