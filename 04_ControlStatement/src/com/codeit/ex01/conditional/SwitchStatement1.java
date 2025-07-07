package com.codeit.ex01.conditional;

public class SwitchStatement1 {
	public static void main(String[] args) {
		// 점수를 1등급 ~ 4등급으로 변환하는 문장
		// 1등급 : 100 ~ 80점, 2등급 : 80 ~ 60점 ...

		double score = 100.0;
		int grade = (int) (score / 20);
		System.out.println("grade : " + grade);

		// switch (이곳) {...} : 이곳에 사용가능한 type은 int, char, String, 열거형이고, double(실수)는 안됨!
		switch (grade) {
			case 5: // 100점
			case 4: // 100점 미만 ~ 80점
				System.out.println("1등급 입니다.");
				break; // break문이 없으면 아래 문장과 같이 실행된다.
			case 3: // 80점 미만 ~ 60점 이상
				System.out.println("2등급 입니다.");
				break;
			case 2: // 60점 미만 ~ 40점 이상
				System.out.println("3등급 입니다.");
				break;
			case 1: // 40점 미만 ~ 0점 이상
			case 0: // 0점
				System.out.println("4등급 입니다.");
				break;
			default: // case에 해당되지 않는 예외값 처리
				System.out.println(grade + "값은 예외입니다");
				// 이곳은 break문이 필요없음.
		}

	}
}