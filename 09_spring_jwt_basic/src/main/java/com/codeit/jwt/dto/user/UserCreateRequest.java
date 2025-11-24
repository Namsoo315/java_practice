package com.codeit.jwt.dto.user;

import lombok.Builder;

@Builder
public record UserCreateRequest(
        String username,
        String email,
        String password,
        String role   // null이면 USER
) {}
