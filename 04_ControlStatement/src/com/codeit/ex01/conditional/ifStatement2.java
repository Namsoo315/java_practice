package com.codeit.ex01.conditional;

import java.util.Scanner;

public class ifStatement2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("숫자를 입력해주세요 : ");
		int value = sc.nextInt();

		if (value > 0) {
			System.out.println("양수입니다.");
		} else if (value == 0) {
			System.out.println("0입니다.");
		} else {
			System.out.println("음수입니다.");
		}

		if (value < 100) {
			System.out.println("100보다 작습니다.");
		} else if (value < 1000) {
			System.out.println("1000보다 작습니다.");
		} else if (value < 10000) {
			System.out.println("10000보다 작습니다.");
		} else {
			System.out.println("10000이상 입니다.");
		}

		if(value >= 10000){
			System.out.println("10000이상 입니다.");
		}else if(value >= 1000){
			System.out.println("1000이상 입니다.");
		}else if(value >= 100){
			System.out.println("100이상 입니다.");
		}else{
			System.out.println("100미만 입니다.");
		}
	}
}
