package com.codeit.rest.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

// 응답 결과를 일정한 포멧으로 처리하기 위한 응답 공통 설계
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "공통 API 응답 객체")
public class ApiResult<T> {

    @Schema(description = "요청 성공 여부", example = "true")
    private boolean success;    // 응답의 성공 여부

    @Schema(description = "응답 메시지", example = "정상적으로 처리가 완료되었습니다.")
    private String message;    // 응답 메시지

    @Schema(description = "응답 데이터", nullable = true)
    private T data;            // 실제 body 영역

    @Schema(description = "에러 정보", nullable = true, implementation = ApiResult.class)
    private ApiError error;    // 에러 메시지

    @Schema(description = "응답 시간 (UTC)", example = "2025-08-18T17:30:00.123Z")
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
