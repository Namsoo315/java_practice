package com.codeit.blog.dto.comment;

import com.querydsl.core.types.Order;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CommentSearchRequest(
        Long postId,
        Long userId,
        String content,
        String nickname,
        Order direction,
        Integer cursor,
        Instant after,
        Integer limit
) {
    public CommentSearchRequest {
        if (direction == null) direction = Order.DESC;
        if (cursor == null || cursor < 0) cursor = 0;
        if (limit == null || limit <= 0 || limit > 200) limit = 50;
    }
}
