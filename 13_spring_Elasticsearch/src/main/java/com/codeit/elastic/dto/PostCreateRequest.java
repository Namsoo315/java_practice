package com.codeit.elastic.dto;

import com.codeit.elastic.entity.Category;

public record PostCreateRequest(
        String title,
        String content,
        String tags,
        Category category,
        Long authorId
) {
}
