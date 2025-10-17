package com.codeit.blog.dto.post;

import com.querydsl.core.types.Order;
import lombok.Builder;

import java.time.Instant;

@Builder
public record PostSearchRequest(
        String title,
        String content,
        String nickname,
        // Sort.Direction도 존재 < JPQL 쓸때
        Order direction,
        Integer cursor,
        Instant after,
        Integer limit
) { }
