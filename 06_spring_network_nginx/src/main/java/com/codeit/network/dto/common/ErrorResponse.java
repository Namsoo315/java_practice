package com.codeit.network.dto.common;

public record ErrorResponse(
        String code,
        String message
) {}
