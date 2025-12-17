package com.codeit.elastic.event;

public record PostIndexEvent(
        Long postId,
        EventType type
) {
}