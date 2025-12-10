package com.codeit.graphql.dto.post;

public record PostCreateRequest(
        Long authorId,
        String title,
        String content,
        Boolean isPublished
) {
}
