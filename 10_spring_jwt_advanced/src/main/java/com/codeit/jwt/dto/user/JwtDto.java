package com.codeit.jwt.dto.user;

public record JwtDto(
    UserDto userDto,
    String accessToken
) {
}