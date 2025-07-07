package com.codeit.ex06.bit_operator.bit_operator;

// bit 연산자 정리
public class BitOperator {
	public static void main(String[] args) {
		int num1 = 0b1; // 0b : 2진수,
		int num2 = 0x1f; // 0x : 16진수,
		int num3 = 127; //  10진수
//		int num4 = 011; // 0 : 8진수,

		System.out.println("기본형");
		System.out.println("num1 : " + num1);
		System.out.println("num2 : " + num2);
		System.out.println("num3 : " + num3 + "\n");

		System.out.println("2진수, 8진수, 16진수 표현");
		System.out.println("num1 : 0b" + Integer.toBinaryString(num1)); // 2진수
		System.out.println("num2 : 0b" + Integer.toBinaryString(num2));
		System.out.println("num3 : 0b" + Integer.toBinaryString(num3));
		System.out.println("num3 : 0o" + Integer.toOctalString(num3)); // 8진수
		System.out.println("num3 : 0x" + Integer.toHexString(num3)  + "\n" ); // 16진수

		System.out.println("&연산 : 논리 and 연산 같음, 네트워크에서는 mask라 불림");
		System.out.println("num1 : 0b" + Integer.toBinaryString(num1 & 0b111)); // 8로 mask하는 연산
		System.out.println("num2 : 0b" + Integer.toBinaryString(num2 & 0b111));
		System.out.println("num3 : 0b" + Integer.toBinaryString(num3 & 0b111) + "\n");

		System.out.println("|연산 : 논리 or 연산 같음");
		System.out.println("num1 : 0b" + Integer.toBinaryString(num1 | 0b111));
		System.out.println("num2 : 0b" + Integer.toBinaryString(num2 | 0b111));
		System.out.println("num3 : 0b" + Integer.toBinaryString(num3 | 0b111)+ "\n");

		System.out.println("^연산 : xor연산");
		System.out.println("num1 : 0b" + Integer.toBinaryString(num1 ^ 0b111));
		System.out.println("num2 : 0b" + Integer.toBinaryString(num2 ^ 0b111));
		System.out.println("num3 : 0b" + Integer.toBinaryString(num3 ^ 0b111)+ "\n");

		System.out.println("~연산 : not연산");
		System.out.println("num1 : 0b" + Integer.toBinaryString(~num1));
		System.out.println("num2 : 0b" + Integer.toBinaryString(~num2));
		System.out.println("num3 : 0b" + Integer.toBinaryString(~num3 )+ "\n");

		System.out.println("<<연산 : left shift 연산, 비트들을 전부 왼쪽으로 이동시킴");
		System.out.println("num1 : 0b" + Integer.toBinaryString(num1 << 1));
		System.out.println("num2 : 0b" + Integer.toBinaryString(num2 << 1));
		System.out.println("num3 : 0b" + Integer.toBinaryString(num3 << 1) + "\n");

		System.out.println(">>연산 : right shift 연산, 비트들을 전부 오른쪽으로 이동시킴. ");
		System.out.println("num1 : 0b" + Integer.toBinaryString(num1 >> 1));
		System.out.println("num2 : 0b" + Integer.toBinaryString(num2 >> 1));
		System.out.println("num3 : 0b" + Integer.toBinaryString(num3 >> 1) + "\n");

		System.out.println("<<<연산 : left shift 연산 + 부호 유지");
		System.out.println("7 : 0b" + Integer.toBinaryString(7 << 1));
		System.out.println("-7 : 0b" + Integer.toBinaryString(-7 << 1));
		System.out.println("7 : " + (7 << 1));
		System.out.println("-7 : " + (-7 << 1)+"\n");

		System.out.println(">>>연산 : right shift 연산 + 부호 유지");
		System.out.println("7 : 0b" + Integer.toBinaryString(7 >>> 1));
		System.out.println("-7 : 0b" + Integer.toBinaryString(-7 >>> 1));
		System.out.println("7 : " + (7 >>> 1));
		System.out.println("-7 : " + (-7 >>> 1)+"\n");

	}
}
