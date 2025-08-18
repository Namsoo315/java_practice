package com.codeit.rest.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
	private String code;		// not found, bad Request
	private String message;		// 에러 원인 출력 ex) 사용자 id를 찾을 수 없습니다.
}
