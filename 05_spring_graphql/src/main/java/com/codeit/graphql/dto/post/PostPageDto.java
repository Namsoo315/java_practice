package com.codeit.graphql.dto.post;

import java.util.List;

public record PostPageDto(
        List<PostDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
