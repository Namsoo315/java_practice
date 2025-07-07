package com.codeit.ex01.conditional;

public class SwitchStatement4 {

	public static void main(String[] args) {

		double score = 100.0;
		int grade = (int) (score / 20); // 100->5, 99->4, 80->4

		// java 12의 arrow switch 표현식
		String result = switch (grade) {
			case 5, 4 -> "1등급";
			case 3 -> "2등급";
			case 2 -> "3등급";
			case 1, 0 -> "4등급";
			default -> "예외";
		};
		System.out.println("등급 : " + result);

	}
}
