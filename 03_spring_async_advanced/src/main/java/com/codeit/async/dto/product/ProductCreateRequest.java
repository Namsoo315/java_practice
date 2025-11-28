package com.codeit.async.dto.product;

public record ProductCreateRequest(
        String name,
        String description,
        Double price,
        Integer quantity
) {
}
