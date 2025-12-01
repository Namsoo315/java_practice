package com.codeit.cache.dto.post;

public record PostCreateRequest(
        String title,
        String content,
        Long authorId
) {}
