package com.codeit.ex07.operator_precedence.operator_precedence;


// 연산자 우선순위
public class OperatorPrecedence {
	
	public static void main(String[] args) {
		// 1. 사칙연산 우선순위 
		System.out.println(1 + 2 * 3); // 곱셈이 높다.
		
		// 2. 단항연산자 우선순위
		int a = 10;
		System.out.println(++a * 10); // 단항이 높다!
		
		// 3. 산술 논리가 복합 될 때
		System.out.println(3 + 4 > 5); // 산술이 논리보다 높다. 
		System.out.println(3 + 4 == 7); // 산술이 높다.
		System.out.println((3 + 4) == 5); // 괄호 사용을 통해 우선순위 명시적으로 표현도 가능 
		
		// 결론 : 복잡하거나 복합될때 가급적이면 괄호를 써서 우선순위를 스스로 정리한다.
	}
}
