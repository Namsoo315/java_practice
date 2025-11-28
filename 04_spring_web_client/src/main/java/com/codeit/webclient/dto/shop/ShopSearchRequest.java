package com.codeit.webclient.dto.shop;


public record ShopSearchRequest(
        String query,
        Integer display,
        Integer start,
        String sort,     // sim, date, asc, dsc
        String filter,   // optional
        String exclude   // optional
) {
    public int displayOrDefault() {
        return (display == null || display < 1) ? 10 : display;
    }

    public int startOrDefault() {
        return (start == null || start < 1) ? 1 : start;
    }

    public String sortOrDefault() {
        return (sort == null || sort.isBlank()) ? "sim" : sort;
    }
}

