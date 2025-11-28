package com.codeit.async.dto.user;

public record SignUpRequest(
        String username,
        String password,
        String email,
        String address
) {
}