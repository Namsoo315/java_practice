package com.codeit.elastic.dto;

import com.codeit.elastic.entity.Category;

public record PostUpdateRequest(
        String title,
        String content,
        String tags,
        Category category
) {
}
