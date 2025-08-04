package ex01.parsing.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 매핑 되지 않는 멤버 무시하는 방법
public class BoxOfficeResult {
	@JsonIgnore // 해당 필드를 무시하는 어노테이션
	private String title;

	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd") // 문자열로 들어온 날짜를 LocalDate으로 변환하는 어노테이션
	private LocalDate openDate;

	// 이름이 일치하면 JsonProperty 필요 없음
	private String boxofficeType;                // 박스오피스 종류 (주말/주중)
	private String showRange;                    // 집계 범위
	private String yearWeekTime;                 // 연도+주차

	@JsonProperty("weeklyBoxOfficeList") // 원래 이름
	private List<BoxOffice> list; // 영화 리스트
}