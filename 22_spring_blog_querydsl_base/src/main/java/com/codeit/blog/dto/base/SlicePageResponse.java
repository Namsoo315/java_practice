package com.codeit.blog.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class SlicePageResponse<T> {
    private String nextCursor;
    private Instant nextAfter;
    private int size;
    private long totalElements;
    private boolean hasNext;
    private List<T> content;
}