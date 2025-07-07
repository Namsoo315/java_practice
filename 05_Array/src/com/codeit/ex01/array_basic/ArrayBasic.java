package com.codeit.ex01.array_basic;

import java.util.Arrays;

public class ArrayBasic {
	public static void main(String[] args) {
		System.out.println("1. 배열 기본 생성법");

		int[] array = null;    //null로 초기화 or 참조형
		array = new int[10];

		//new 생성 안할시 NullpointerExce[p
		System.out.println(array[0]);
		System.out.println(array[1]);
		System.out.println("------------------------------");
		array[0] = 0;
		array[1] = 1;
		array[2] = 2;

		for (int i = 0; i < array.length; i++) {
			array[i] = i;
			System.out.println("array = " + array[i]);
		}

		System.out.println("------------------------------");
		System.out.println("2. 배열 리터럴로 생성하는 법");
		int[] array2 = new int[10];
		int[] array3 = {1, 2, 3, 4, 5, 6,};
		int[] array4 = new int[] {1, 2, 3, 4, 5, 6};
		// int[] array5 = new int[6] {1, 2, 3, 4, 5, 6}; 크기 지정시 에러

		for (int i = 0; i < array3.length; i++) {
			System.out.println("array = " + array3[i]);
		}

		System.out.println("3. 배열 응용 메서드");
		int[] array5 = new int[10];
		for (int i = 0; i < array5.length; i++) {
			array5[i] = i;
		}

		System.out.println("array5 = " + array5);
		String str = Arrays.toString(array5);
		System.out.println("str = " + str);

		char[] charArrays1 = new char[10];
		char[] charArrays2 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		System.out.println(Arrays.toString(charArrays2));

		String[] strArray1 = new String[10];
		String[] strArray2 = {"a", "b", "c", "d", "e", "f", "g", "h"};
		System.out.println(Arrays.toString(strArray2));
	}
}
