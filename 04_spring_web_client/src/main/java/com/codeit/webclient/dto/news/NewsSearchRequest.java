package com.codeit.webclient.dto.news;

public record NewsSearchRequest(
        String query,
        Integer display,
        Integer start,
        String sort
) {
    public int displayOrDefault() {
        return display == null ? 10 : display;
    }

    public int startOrDefault() {
        return start == null ? 1 : start;
    }

    public String sortOrDefault() {
        return (sort == null || sort.isBlank()) ? "sim" : sort;
    }
}

