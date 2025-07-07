package com.codeit.ex04.multi_array_basic;

import java.util.Arrays;

public class MultiDimensionalArray {
	public static void main(String[] args) {
		int[][] arr = new int[10][10];

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = i * 10 + j;
				System.out.print(arr[i][j] + ", ");
			}
			System.out.println();
		}

		System.out.println("----------------------------------");

		int[][] array2 = new int[10][];
		System.out.println(Arrays.toString(array2));

		for(int i = 0; i<array2.length;i++){
			array2[i] = new int[i];
			for(int j = 0; j<array2[i].length;j++){
				array2[i][j] = i * 10 + j;
				System.out.print(array2[i][j] + " ");
			}
			System.out.println();
		}

		String[][] str_array1 = new String[5][5];
		String[][] str_array2;

		for(int i = 0; i<str_array1.length;i++){
			str_array2 = Arrays.copyOf(str_array1, str_array1.length);
		}
	}
}
