package ex01.parsing.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BoxOffice {
	private Integer rnum;             // 순위 번호
	private Integer rank;             // 순위
	private Integer rankInten;        // 순위 변동
	private String 	rankOldAndNew;     // 순위 OLD or NEW
	private String 	movieCd;           // 영화 코드
	private String 	movieNm;           // 영화명
	private String 	openDt;            // 개봉일
	private Long 	salesAmt;            // 매출 금액
	private Double 	salesShare;        // 매출 점유율
	private Long 	salesInten;          // 매출 변동 금액
	private Double 	salesChange;       // 매출 변동율
	private Long 	salesAcc;            // 누적 매출 금액
	private Long 	audiCnt;             // 관객 수
	private Long 	audiInten;           // 관객 수 변동
	private Double 	audiChange;        // 관객 수 변동율
	private Long 	audiAcc;             // 누적 관객 수
	private Long 	scrnCnt;             // 상영관 수
	private Long 	showCnt;             // 상영 횟수

	public BoxOffice(Integer rnum, Integer rank, Integer rankInten, String rankOldAndNew, String movieCd,
		String movieNm,
		String openDt, Long salesAmt, Double salesShare, Long salesInten, Double salesChange, Long salesAcc,
		Long audiCnt,
		Long audiInten, Double audiChange, Long audiAcc, Long scrnCnt, Long showCnt) {
		this.rnum = rnum;
		this.rank = rank;
		this.rankInten = rankInten;
		this.rankOldAndNew = rankOldAndNew;
		this.movieCd = movieCd;
		this.movieNm = movieNm;
		this.openDt = openDt;
		this.salesAmt = salesAmt;
		this.salesShare = salesShare;
		this.salesInten = salesInten;
		this.salesChange = salesChange;
		this.salesAcc = salesAcc;
		this.audiCnt = audiCnt;
		this.audiInten = audiInten;
		this.audiChange = audiChange;
		this.audiAcc = audiAcc;
		this.scrnCnt = scrnCnt;
		this.showCnt = showCnt;
	}

	@Override
	public String toString() {
		return "BoxOffice{" +
			"rnum=" + rnum +
			", rank=" + rank +
			", rankInten=" + rankInten +
			", rankOldAndNew='" + rankOldAndNew + '\'' +
			", movieCd='" + movieCd + '\'' +
			", movieNm='" + movieNm + '\'' +
			", openDt='" + openDt + '\'' +
			", salesAmt=" + salesAmt +
			", salesShare=" + salesShare +
			", salesInten=" + salesInten +
			", salesChange=" + salesChange +
			", salesAcc=" + salesAcc +
			", audiCnt=" + audiCnt +
			", audiInten=" + audiInten +
			", audiChange=" + audiChange +
			", audiAcc=" + audiAcc +
			", scrnCnt=" + scrnCnt +
			", showCnt=" + showCnt +
			'}';
	}
}