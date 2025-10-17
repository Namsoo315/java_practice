package com.codeit.blog.dto.post;

import com.codeit.blog.entity.Category;
import lombok.Builder;

@Builder
public record PostCreateRequest(
        String title,
        String content,
        String tags,
        Category category,
        Long authorId
) { }
