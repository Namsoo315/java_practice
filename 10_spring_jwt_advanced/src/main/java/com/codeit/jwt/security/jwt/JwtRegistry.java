package com.codeit.jwt.security.jwt;


import com.codeit.jwt.dto.user.JwtInformation;

// JWT 기반 세션 관리 인터페이스
public interface JwtRegistry<T> {

  void registerJwtInformation(JwtInformation jwtInformation);

  void invalidateJwtInformationByUserId(T userId);

  boolean hasActiveJwtInformationByUserId(T userId);

  boolean hasActiveJwtInformationByAccessToken(String accessToken);

  boolean hasActiveJwtInformationByRefreshToken(String refreshToken);

  void rotateJwtInformation(String refreshToken, JwtInformation newJwtInformation);

  void clearExpiredJwtInformation();
}
