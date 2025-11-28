package com.codeit.async.event;


public record OrderCompletedEvent(
        Long orderId,
        Long userId,
        Long productId,
        int quantity
) {
}