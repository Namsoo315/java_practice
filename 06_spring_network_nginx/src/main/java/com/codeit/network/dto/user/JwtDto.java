package com.codeit.network.dto.user;

public record JwtDto(
    UserDto userDto,
    String accessToken
) {
}