package com.codeit.ex02.array_copy;

import java.util.Arrays;

public class Arraycopy {
	public static void main(String[] args) {
		// 얕은 복사 (shallow copy)
		int[] array1 = {0, 1, 2, 3, 4, 5, 6};
		int[] array2 = array1;		//얕은 복사

		System.out.println("변경 전");
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));

		array1[0] = 5;
		System.out.println("변경 후");
		System.out.println(Arrays.toString(array1));
		System.out.println(Arrays.toString(array2));

		System.out.println("--------------------------------");

		//깊은 복사 (deep copy)
		System.out.println("깊은 복사");
		int[] array3 = {1,2,3,4,5,6,7};
		int[] array4 = null;		//깊은 복사 대상.
		array4 = Arrays.copyOf(array3, array3.length);

		System.out.println("변경 전");
		System.out.println(Arrays.toString(array3));
		System.out.println(Arrays.toString(array4));

		array3[0] = 5;

		System.out.println("변경 후");
		System.out.println(Arrays.toString(array3));
		System.out.println(Arrays.toString(array4));
	}
}
