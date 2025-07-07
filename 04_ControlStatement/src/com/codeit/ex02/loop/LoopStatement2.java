package com.codeit.ex02.loop;

public class LoopStatement2 {
	// 0 ~ 9까지 홀짝 세기를 while, do-while, for문으로 구현하기
	public static void main(String[] args) {
		testWhileLoop();
		testDoWhileLoop();
		testForLoop();
	}

	private static void testWhileLoop() {
		int i = 0;
		while(i < 10) {
			if(i % 2 == 0) {
				System.out.println(i + "는 짝수입니다.");
			} else {
				System.out.println(i + "는 홀수입니다.");
			}
			i++;
		}
	}

	private static void testDoWhileLoop() {
		int i = 0;

		do {
			if(i % 2 == 0) {
				System.out.println(i + "는 짝수입니다.");
			} else {
				System.out.println(i + "는 홀수입니다.");
			}
			i++;
		} while(i < 10);

	}

	private static void testForLoop() {
		// 먼저 기계적으로 기본 for문을 타이핑 한다.
		for(int i = 0; i < 10; i++) {
			if(i % 2 == 0) {
				System.out.println(i + "는 짝수입니다.");
			} else {
				System.out.println(i + "는 홀수입니다.");
			}
		}
	}
}