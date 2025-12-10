package com.codeit.graphql.dto.comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        Long postId,
        Long authorId,
        String content,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
