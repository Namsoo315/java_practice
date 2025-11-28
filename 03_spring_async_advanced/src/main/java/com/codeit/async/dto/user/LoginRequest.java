package com.codeit.async.dto.user;


public record LoginRequest(
        String username,
        String password
) {
}
