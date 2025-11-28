package com.codeit.async.dto.order;

public record InventoryResponse(
        Long productId,
        boolean inStock,
        int availableQuantity
) {
}
