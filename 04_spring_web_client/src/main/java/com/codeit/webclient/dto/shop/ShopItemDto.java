package com.codeit.webclient.dto.shop;

public record ShopItemDto(
        String title,
        String link,
        String image,
        int lowPrice,
        int highPrice,
        String mallName
) {
    public static ShopItemDto from(NaverShopItemDto item) {
        return new ShopItemDto(
                item.title(),
                item.link(),
                item.image(),
                item.lprice(),
                item.hprice(),
                item.mallName()
        );
    }
}
