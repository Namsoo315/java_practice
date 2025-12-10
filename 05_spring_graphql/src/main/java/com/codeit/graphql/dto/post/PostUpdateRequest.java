package com.codeit.graphql.dto.post;

public record PostUpdateRequest(
        String title,
        String content,
        Boolean isPublished
) {
}
