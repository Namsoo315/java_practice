package com.codeit.webclient.dto.news;

public record NewsArticleDto(
        String title,
        String link,
        String summary,
        String publishedAt,
        String originLink
) {
    public static NewsArticleDto from(NaverNewsItem item) {
        return new NewsArticleDto(
                item.title(),
                item.link(),
                item.description(),
                item.pubDate(),
                item.originallink()
        );
    }
}