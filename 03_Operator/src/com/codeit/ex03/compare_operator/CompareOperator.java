package com.codeit.ex03.compare_operator;

// 비교연산자
public class CompareOperator {
	public static void main(String[] args) {
		int a = 100;
		int b = 200;

		System.out.println("a == b = " + (a == b));
		System.out.println("a != b = " + (a != b));
		System.out.println("-------------------------------");

		String str1 = "안녕하세요?";	//String pool
		String str2 = "안녕하세요?";	//String pool
		String str3 = new String("안녕하세요?"); //heap -> string pool

		System.out.println("str1 == str2 = " + (str1 == str2));
		System.out.println("str2 == str3 = " + (str2 == str3));
		System.out.println("str1 + str2 + str3 = " + str1 + str2 + str3);

		//java에서 문자열 비교할려면
		System.out.println("str1.equals(str2) = " + str1.equals(str2));
		System.out.println("str1.equals(str3) = " + str1.equals(str3));
		
		//문자열이 포함되었는지 확인하는 방법
		String str4 = "안녕";
		System.out.println("str1.contains(str4) = " + str1.contains(str4));
	}
}
