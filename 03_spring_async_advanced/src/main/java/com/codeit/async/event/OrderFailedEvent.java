package com.codeit.async.event;

public record OrderFailedEvent(
        Long userId,
        Long productId,
        int quantity,
        String reason
) {
}
