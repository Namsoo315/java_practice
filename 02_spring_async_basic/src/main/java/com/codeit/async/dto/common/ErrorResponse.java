package com.codeit.async.dto.common;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String path,
        String message,
        Instant timestamp
) {
}
