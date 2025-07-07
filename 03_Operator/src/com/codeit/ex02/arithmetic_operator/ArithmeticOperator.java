package com.codeit.ex02.arithmetic_operator;


// 산순연산 : +-*/% (오칙연산)
public class ArithmeticOperator {

	public static void main(String[] args) {
		int a = 10;
		int b = 2;
		int c = 0;
		
		System.out.println("사칙 연산");
		System.out.println(a + b);
		System.out.println(a - b);
		System.out.println(a * b); // * : 곱셈
		System.out.println(a / b); // / : 나눗셈
		System.out.println(a / 3); // 10 / 3 = 3, int type으로 소수점 버려짐!
		System.out.println(a / 3.0); 	   // 소수점 출력방법 1 - double로 3.0 사용
		System.out.println((double)a / 3); // 소수점 출력방법 2 - int값 중 하나를 double로 캐스팅!
		System.out.println(a / (double)3);
		System.out.println((double)(a / 3)); // 안되는 표현!!! a/3을 실행하고 캐스트함으로 잘못됨
		
		// / by zero, 0으로 나눌수 없다!
//		System.out.println(a / 0); // java.lang.ArithmeticException
		
		
		System.out.println("%(모듈러) 연산");
		
		c = 10 % 3; // 나머지 연산값 : 1
		System.out.println(c);
		
		System.out.println(c); // 0이면 짝수
		
		c = 11 % 2; 
		System.out.println(c); // 1이면 홀수
		
		c = 9 % 3; // 3의 배수인지 확인할때
		System.out.println(c); // 0이면 3의 배수

		// ※ 정수 + 실수 연산시의 주의점
		// - 사칙 연산 시에는 type이 큰쪽으로 자동으로 형변환 된다.
		// - double > float > long > int > short > byte
		float pi = 3.14f;
		double x = 1.23456789;
		long i = 10;
		
		System.out.println(pi + i); // float + long = (큰거)float
		System.out.println(pi + x); // float + double = (큰거)double
		System.out.println(pi + (float)x); // float + float = float 
		System.out.println((float)(pi + x)); // float 표현법 중 하나
	}
}







