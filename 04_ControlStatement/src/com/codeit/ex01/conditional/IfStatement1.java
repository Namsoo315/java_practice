package com.codeit.ex01.conditional;

public class IfStatement1 {
	public static void main(String[] args) {
		int a = 10;
		if (a == 5) {
			System.out.println("a는 5입니다. ");
		}

		if (a < 0)
			System.out.println("음수입니다.");

		if (a % 2 == 0) {
			System.out.println("짝수입니다.");
		} else {
			System.out.println("홀수입니다.");
		}

		if (a % 2 == 0) {
			System.out.println("짝수입니다.");
		} else if (a % 2 == 1) {
			System.out.println("홀수입니다.");
		}
	}
}
