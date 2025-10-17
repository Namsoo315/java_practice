package com.codeit.blog.dto.comment;

import com.codeit.blog.dto.post.PostOnlyIdResponse;
import com.codeit.blog.dto.user.UserSimpleResponse;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CommentFlatResponse(
        Long id,
        String content,
        Instant createdAt,
        Long postId,
        Long userId,
        String userNickname
) { }
