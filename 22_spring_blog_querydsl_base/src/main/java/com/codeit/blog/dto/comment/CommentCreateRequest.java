package com.codeit.blog.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CommentCreateRequest(
        @NotBlank
        String content,
        @NotBlank
        Long authorId,
        @NotBlank
        Long postId
) { }