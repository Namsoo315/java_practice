package com.codeit.async.event;


import java.time.Instant;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Long productId,
        Integer quantity,
        Double totalAmount,
        String shippingAddr,
        Instant createdAt
) {
}
