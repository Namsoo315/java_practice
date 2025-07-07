package com.codeit.ex02.loop;

public class LoopStatement4 {
	public static void main(String[] args) {
		// for문 응용 -> 2씩 증가하는 반복문
		for(int i = 2; i <= 20; i += 2) {
			System.out.println(i);
		}

		// for문 조건문 지정 안하기 가능
		for(int i = 0; ; i += 2) {
			System.out.println(i);
			if(i == 100) {
				break;
			}
		}

		// for문 무한 반복문 만들기(이것 보단 while로 만드는게 일반적)
		//		for(;;) {
		//			System.out.println("무한루프");
		//		}
	}
}
