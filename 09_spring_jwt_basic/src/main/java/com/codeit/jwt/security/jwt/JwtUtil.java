package com.codeit.jwt.security.jwt;

import com.codeit.jwt.dto.user.UserDto;
import com.codeit.jwt.entity.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// JWT 토큰 생성, 검증, 파싱 관련된 유틸성 Class
@Slf4j
@Component
public class JwtUtil {

  @Value("${security.jwt.secret}")
  private String secret;  // JWT 토큰 키

  @Value("${security.jwt.issuer}")
  private String issuer;  // 토큰 발행자

  @Value("${security.jwt.access-token-validity-seconds}")
  private long accessTokenValiditySeconds;  // 토큰 유효 시간

  private byte[] getSecretKey() {
    return secret.getBytes(StandardCharsets.UTF_8);   // UTF-8로 키 불러오기
  }

  // UserDto -> JWT기반 Token으로 변환
  public JwtObject generateJWTObject(UserDto userDto) {
    Instant issueTime = Instant.now();
    Instant expirationTime = issueTime.plusSeconds(accessTokenValiditySeconds);

    // 실제 토큰 만드는 구간
    JWTClaimsSet claims = new Builder()
        .subject(userDto.username())  // 정해진 표준은 없지만 보통 Username, userId 정도 들어감.
        .issuer(issuer)
        .issueTime(Date.from(issueTime))
        .expirationTime(Date.from(expirationTime))
        // Claim 생성
        .claim("userId", userDto.id())
        .claim("email", userDto.email())
        .claim("role", userDto.role().name())
        // 필요하면 아바타 URL같은 추가 정보도 넣을 수 있다.
        .build();

    try {
      // 검증 과정
      SignedJWT signedJWT = new SignedJWT(
          new JWSHeader(JWSAlgorithm.HS256),
          claims
      );
      signedJWT.sign(
          new MACSigner(getSecretKey()));  // sign(비밀키) 넣는 과정 원래라면 다른 인증과정을 거쳐야함 (간단한 방법임)
      String token = signedJWT.serialize(); // 토큰 생성
      return new JwtObject(issueTime, expirationTime, userDto, token);
    } catch (JOSEException e) {
      log.error("JWT 생성 중 오류 발생 ", e);
      throw new IllegalStateException("토큰 생성 실패", e);
    }
  }

  // 토큰 유효성 검사 (서명, 만료시간)
  public boolean validate(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);

      // 서명 검증
      JWSVerifier verifier = new MACVerifier(getSecretKey()); // 복호화, 검증용!!
      if (!signedJWT.verify(verifier)) {
        // 검증 수행
        // 실패 상황
        log.error("JWT 검증 실패");
        return false;
      }

      JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

      // 만료 시간 검증
      Date exp = claims.getExpirationTime();
      if (exp == null || exp.before(new Date())) {
        log.debug("JWT 만료됨 exp = {}", exp);
        return false;
      }

      return true;
    } catch (ParseException | JOSEException e) {
      log.debug("JWT 검증 예외 발생 {}", e.getMessage());
      return false;
    }
  }

  // token -> JWTObject 파싱하는 메서드
  public JwtObject parse(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);

      // 서명 재검증
      JWSVerifier verifier = new MACVerifier(getSecretKey());
      if(!signedJWT.verify(verifier)) {
        throw new IllegalArgumentException("JWT 서명 검증 실패");
      }

      JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
      Instant issueTime = claims.getIssueTime().toInstant();
      Instant expirationTime = claims.getExpirationTime().toInstant();

      Long userId = claims.getLongClaim("userId");
      String username = claims.getSubject();
      String email = claims.getStringClaim("email");
      String str_role = claims.getClaimAsString("role");
      Role role = Role.valueOf(str_role);

      UserDto userDto = new UserDto(userId, username, email, role);
      return new JwtObject(issueTime, expirationTime, userDto, token);
    } catch (ParseException | JOSEException e) {
      throw new IllegalArgumentException("JWT 파싱 실패");
    }
  }
}
