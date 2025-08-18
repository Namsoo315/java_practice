package com.codeit.rest.dto.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimplePageResponse<E> {
	private int page;			// 요청한 페이지
	private int size;			// item의 사이즈
	private int totalPages;		// 페이지의 총 갯수
	private long totalElements;	// 아이템의 총 갯수
	private List<E> elements;
}
