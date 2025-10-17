package com.codeit.blog.dto.post;

import com.codeit.blog.entity.Category;

import java.time.Instant;

public record PostFlatDetailDto(
        Long id,
        String title,
        String content,
        Category category,
        String tags,
        Instant createdAt,
        Instant updatedAt,
        Long commentCount,
        Long likesCount,
        String authorUsername,
        String authorNickname
) { }
