package com.codeit.ex02.loop;

public class LoopStatement {
	public static void main(String[] args) {
		// myFirstWhileLoop();		//메서드 호출법
		testWhileLoop();
		// testDoWhileLoop();
	}

	private static void testWhileLoop() {
		int i = 0;
		System.out.println("while 루프 시작!");
		while (i < 10) {
			System.out.println(i++);
		}
		System.out.println("while 루프 끝");
	}

	//static 메서드
	public static void myFirstWhileLoop() {
		int count = 0;
		while (true) {
			System.out.println("무한 반복문! count : " + count);
		}
	}

	// doWhile은 잘 안쓴다.
	// doWhile은 최초 한번은 무조건 실행되는 반복문, 이후에는 조건절에 따라 실행 된다.
	private static void testDoWhileLoop() {
		int i = 0;
		do {
			System.out.println(i++);
		} while (i < 10);
		System.out.println("do-while문 끝!");

		i = 100;
		do {
			System.out.println("최초 한번은 실행되는 문장!");
		} while (i < 10);
	}
	private static void testForLoop(){

	}
}
