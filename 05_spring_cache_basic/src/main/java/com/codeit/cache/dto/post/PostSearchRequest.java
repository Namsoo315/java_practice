package com.codeit.cache.dto.post;

public record PostSearchRequest(
        String title,
        String content,
        Integer page,
        Integer pageSize
) {

    public int pageOrDefault() {
        return (page == null || page < 0) ? 0 : page;
    }

    public int pageSizeOrDefault() {
        return (pageSize == null || pageSize <= 0) ? 10 : pageSize;
    }

    public String titleOrNull() {
        return (title == null || title.isBlank()) ? null : title;
    }

    public String contentOrNull() {
        return (content == null || content.isBlank()) ? null : content;
    }
}
