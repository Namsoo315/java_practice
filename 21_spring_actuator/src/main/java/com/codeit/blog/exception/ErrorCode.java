package com.codeit.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  // User 관련 에러 코드
  USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
  DUPLICATE_USER("이미 존재하는 사용자입니다."),
  INVALID_USER_CREDENTIALS("잘못된 사용자 인증 정보입니다."),
  INVALID_USER_PARAMETER("잘못된 사용자 정보입니다"),

  // Server 에러 코드
  INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다."),
  INVALID_REQUEST("잘못된 요청입니다.");

  // User 관련 에러코드
  private final String message;
}
