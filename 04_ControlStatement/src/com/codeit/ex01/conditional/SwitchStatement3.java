package com.codeit.ex01.conditional;

import java.util.Scanner;

public class SwitchStatement3 {
	// 상수를 모든 영역에서 공통적으로 활용할때 사용하는 글로벌 변수
	public static final int MEN   = 1;
	public static final int WOMEN = 2;
	public static final int OTHER = 3;

	public static void main(String[] args) {
		// 성별에 해당하는 문자를 입력받아 남성/여성/제3의 성으로 분류하는 코드
		
		Scanner sc = new Scanner(System.in);
		System.out.println("성별을 입력해주세요.");
		String str = sc.nextLine();
		int gender = 0;

		switch(str) {
			case "남" : case "남성" : case "남자" :
			case "아저씨" : case "총각" : case "소년" :
				gender = MEN;
				break;
			case "여" : case "여성" : case "여자" :
			case "아가씨" : case "소녀" :
				gender = WOMEN;
				break;
			default :
				gender = OTHER;
				break;
			//			return; // 리턴문을 실행하면 아래 출력 문장이 출력되지 않는다!
		}
		System.out.println("gender : " + gender);


		// switch 문 단축 표현 java 17 version↑
		int gender2 = switch (str) {
            case "남", "남성", "남자", "아저씨", "총각", "소년" -> MEN;
            case "여", "여성", "여자", "아가씨", "소녀" -> WOMEN;
            default -> OTHER;
            //			return; // 리턴문을 실행하면 아래 출력 문장이 출력되지 않는다!
        };
		System.out.println("gender2 : " + gender2);

		if(gender == MEN) {
			System.out.println("남성입니다.");
		} else if(gender == WOMEN) {
			System.out.println("여성입니다.");
		} else {
			System.out.println("제3의 성입니다.");
		}
	}
}
