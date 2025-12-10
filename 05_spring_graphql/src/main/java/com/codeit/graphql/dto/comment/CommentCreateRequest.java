package com.codeit.graphql.dto.comment;

public record CommentCreateRequest(
        Long postId,
        Long authorId,
        String content
) {
}
