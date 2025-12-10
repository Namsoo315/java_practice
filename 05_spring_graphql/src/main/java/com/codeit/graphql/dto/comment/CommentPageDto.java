package com.codeit.graphql.dto.comment;

import java.util.List;

public record CommentPageDto(
        List<CommentDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
