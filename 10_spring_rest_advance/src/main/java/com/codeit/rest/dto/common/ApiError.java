package com.codeit.rest.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "에러 정보")
public class ApiError {
	@Schema(description = "에러 코드", example = "true")
	private String code;		// not found, bad Request

	@Schema(description = "에러 메시지", example = "사용자가 없습니다.")
	private String message;		// 에러 원인 출력 ex) 사용자 id를 찾을 수 없습니다.
}
