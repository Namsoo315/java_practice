package com.codeit.async.dto;

public record OrderCreateRequest(
        Long userId,
        Long productId,
        Integer quantity,
        String shippingAddr
) {
}