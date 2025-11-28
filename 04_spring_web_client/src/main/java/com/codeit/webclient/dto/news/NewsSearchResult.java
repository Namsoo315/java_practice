package com.codeit.webclient.dto.news;


import java.util.List;

public record NewsSearchResult(
        String query,
        int total,
        int start,
        int display,
        List<NewsArticleDto> articles,
        boolean fromFallback
) {
    public static NewsSearchResult of(
            String query,
            int total,
            int start,
            int display,
            List<NewsArticleDto> articles,
            boolean fromFallback
    ) {
        return new NewsSearchResult(query, total, start, display, articles, fromFallback);
    }
}
