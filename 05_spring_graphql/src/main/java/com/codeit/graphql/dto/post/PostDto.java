package com.codeit.graphql.dto.post;

import java.time.LocalDateTime;

public record PostDto(
        Long id,
        Long authorId,
        String title,
        String content,
        boolean isPublished,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
