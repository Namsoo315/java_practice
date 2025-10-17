package com.codeit.blog.dto.post;

import com.codeit.blog.entity.Category;
import lombok.Builder;

@Builder
public record PostUpdateRequest(
        String title,
        String content,
        String tags,
        Category category
) { }
