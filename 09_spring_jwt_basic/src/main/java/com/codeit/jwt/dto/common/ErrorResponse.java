package com.codeit.jwt.dto.common;

public record ErrorResponse(
        String code,
        String message
) {}
