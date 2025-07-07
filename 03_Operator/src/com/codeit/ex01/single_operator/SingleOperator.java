package com.codeit.ex01.single_operator;

public class SingleOperator {
	public static void main(String[] args) {
		// 1. not 연산자 : 논리값을 토글(Toggle)하는 용도로 활용
		boolean isTrue  = true;

		System.out.println("isTrue = " + isTrue);
		System.out.println("isTrue (!) = " + !isTrue);
		System.out.println("isTrue (!)(!) = " + !!isTrue);

		isTrue = !isTrue;
		System.out.println("isTrue = " + isTrue);
		System.out.println("--------------------------------------");
		
		// 2. 증감 연산자 ++ --, 전위 후위증감연산자
		// -> 한줄에 하나씩 쓰기
		int num = 10;
		System.out.println("num++ = " + num++);	//출력 10 후 +1
		System.out.println("num++ = " + num++);	//출력 11 후 +1
		System.out.println("++num = " + ++num);	//전 +1 출력 13
		System.out.println("++num = " + ++num);	//전 +1 출력 14

		//부호 연산 + , -
		num = +10;
		System.out.println("+num = " + num);
		num = -10;
		System.out.println("-num = " + num);

	}
}
