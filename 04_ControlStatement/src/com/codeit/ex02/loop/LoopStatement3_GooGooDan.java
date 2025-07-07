package com.codeit.ex02.loop;

public class LoopStatement3_GooGooDan {
	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.println(i + " X " + j + " = " + (i * j));
			}
		}

		System.out.println("----------------------------------------------");
		for (int i = 2; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				System.out.println(i + " X " + j + " = " + (i * j));
			}
		}

		System.out.println("----------------------------------------------");

		for(int i = 2; i < 10; i++){
			System.out.println("---------------"+ i + "단시작---------------");
			for(int j = 1; j < 10; j++){
				System.out.println(i + " X " + j + " = " + (i * j));
			}
			System.out.println("------------------------------------\n");
		}

	}
}
