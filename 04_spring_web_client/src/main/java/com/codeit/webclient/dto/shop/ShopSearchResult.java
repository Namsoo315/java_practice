package com.codeit.webclient.dto.shop;

import java.util.List;

public record ShopSearchResult(
        String query,
        int total,
        int start,
        int display,
        List<ShopItemDto> items,
        boolean fallback
) {
    public static ShopSearchResult of(
            String query,
            int total,
            int start,
            int display,
            List<ShopItemDto> items,
            boolean fallback
    ) {
        return new ShopSearchResult(query, total, start, display, items, fallback);
    }
}
