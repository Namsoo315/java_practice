package com.codeit.elastic.dto.common;

import java.util.List;

public record PagedResult<T>(
        long total,
        int page,
        int size,
        List<T> items
) {
}
