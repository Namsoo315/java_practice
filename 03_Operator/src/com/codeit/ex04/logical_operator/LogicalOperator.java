package com.codeit.ex04.logical_operator;

public class LogicalOperator {
	public static void main(String[] args) {
		boolean x = true;
		boolean y = false;
		boolean z = true;

		System.out.println("x && y = " + (x && y));
		System.out.println("x || y = " + (x || y));
		System.out.println("x && y && z = " + (x && y && z));
		System.out.println("x || y || z = " + (x || y || z));
		System.out.println("x || y && z = " + (x || y && z));	//AND가 더 우선순위 높다
		System.out.println("(x || y) && z = " + ((x || y) && z));

		
		//비교 연산자, 숫자만 가능
		int a = 100;
		int b = 50;
		System.out.println("a > b = " + (a > b));
		System.out.println("a < b = " + (a < b));
		System.out.println("a >= 100 = " + (a >= 100));
		System.out.println("a <= 100 = " + (a <= 100));
		System.out.println("!(a > b) = " + !(a > b));
		System.out.println("!(a < b) = " + !(a < b));
		System.out.println("--------------------------------");

		//비교 연산자 3개 비교하기
		a = 100;
		b = 50;
		int c = 70;
		// c 는 a보다 작고, b보다 c가 큰가?
		System.out.println("c < a && c > b = " + (c < a && c > b));

	}
}
