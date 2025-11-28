package com.codeit.webclient.dto.shop;

import java.util.List;

public record NaverShopResponse(
        String lastBuildDate,
        int total,
        int start,
        int display,
        List<NaverShopItemDto> items
) {}

