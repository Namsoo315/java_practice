package com.codeit.async.dto;


import java.time.Instant;

public record OrderResponse(
        Long id,
        String status,
        Long userId,
        Long productId,
        Integer quantity,
        Double unitPrice,
        Double totalAmount,
        String shippingAddr,
        Instant createdAt
) {
}