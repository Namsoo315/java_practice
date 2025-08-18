package com.codeit.rest.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

// 응답 결과를 일정한 포멧으로 처리하기 위한 응답 공통 설계
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private boolean success;    // 응답의 성공 여부
    private String message;    // 응답 메시지
    private T data;            // 실제 body 영역
    private ApiError error;    // 에러 메시지
    private Instant timestamp;  // Rest 응답 시간

    // 공통 API를 생성하기 위한 생성 메서드

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.<T>builder()
                .success(true)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> ApiResult<T> ok( T data, String message) {
        return ApiResult.<T>builder()
                .success(true)
                .data(data)
                .timestamp(Instant.now())
                .message(message)
                .build();
    }

    public static <T> ApiResult<T> ok(String message) {
        return ApiResult.<T>builder()
                .success(true)
                .timestamp(Instant.now())
                .message(message)
                .build();
    }

    public static <T> ApiResult<T> fail(String code, String message){
        return ApiResult.<T>builder()
            .success(false)
            .error(new ApiError(code, message))
            .timestamp(Instant.now())
            .build();
    }
}
