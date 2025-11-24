package com.codeit.jwt.security.jwt;

import com.codeit.jwt.dto.user.UserDto;

import java.time.Instant;

public record JwtObject(
    Instant issueTime,
    Instant expirationTime,
    UserDto userDto,
    String token
) {
  public boolean isExpired() {
    return expirationTime.isBefore(Instant.now());
  }
}