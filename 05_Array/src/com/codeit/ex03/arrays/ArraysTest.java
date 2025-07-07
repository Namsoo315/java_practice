package com.codeit.ex03.arrays;

import java.util.Arrays;
import java.util.Random;

public class ArraysTest {

    public static void main(String[] args) {
        // Arrays에서 유용한 방법들 정리

        // fill = 값 채우기
        int[] array = new int[10];
        Arrays.fill(array, 10);
        System.out.println(Arrays.toString(array));

        // compare : 배열이 같은지 비교하는 메소드
        int[] array1 = new int[10];
        int[] array2 = new int[10];
        Arrays.fill(array1, 5);
        Arrays.fill(array2, 10);
        System.out.println(Arrays.compare(array1, array1)); // true
        System.out.println(Arrays.compare(array1, array2)); // false

        // sort : 정렬
        // 1. 난수로 배열 채우기
//        Random r = new Random();
        Random r = new Random(System.currentTimeMillis()); // 현재 시간으로 seed값 초기화
        int[] array3 = new int[10];
        for (int i = 0; i < array3.length; i++) {
            array3[i] = r.nextInt(10);
        }
        System.out.println(Arrays.toString(array3));
        Arrays.sort(array3);
        System.out.println(Arrays.toString(array3));

        // binarySearch() : 정렬되어 있지 않으면 사용 불가능!
        int[] array6 = new int[10];
        for (int i = 0; i < array6.length; i++) {
            array6[i] = r.nextInt(10);
        }
        // 특정값이 있는지 없는지 확인하는 방법
        System.out.println("array6");
        System.out.println(Arrays.toString(array6));
        Arrays.sort(array6);
        System.out.println(Arrays.toString(array6));
        int index = Arrays.binarySearch(array6, 5); // 5가 있는지 확인하는 방법
        if(index >= 0){ // 값이 있음
            System.out.println("값을 찾았습니다. index : "  + index);
        } else { // 값이 없음
            System.out.println("값을 못찾았습니다. index : "  + index);
        }
    }
}
