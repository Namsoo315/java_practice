package com.codeit.async.dto.user;

public record SignUpResponse(
        Long id,
        String username,
        String email,
        String address,
        String role
) {
}