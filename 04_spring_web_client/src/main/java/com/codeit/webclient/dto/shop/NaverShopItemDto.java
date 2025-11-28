package com.codeit.webclient.dto.shop;


public record NaverShopItemDto(
        String title,
        String link,
        String image,
        int lprice,
        int hprice,
        String mallName,
        String productId,
        int productType,
        String brand,
        String maker,
        String category1,
        String category2,
        String category3,
        String category4
) {}

