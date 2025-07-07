package com.codeit.ex03.branch;

// for loop 네이밍 다는 방법 / flag(깃발) 기법 
public class BranchStatement2 {
	
	public static void main(String[] args) {
		// 반복문 예시 : 중첩된 반복문을 구현하다 보면, 안쪽에 반복문에서 외부 반복문을 종료 시켜야 할 때가 있다.
		
		// 가장 비효율 + 좋지 않은 방법 = 내부 종료조건을 외부에서도 체크한다.
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(i == 3) {
					break;
				}
				System.out.println("\t i : " + i + ", j : " + j);
			}
			if(i == 3) {
				break;
			}
		}
		System.out.println("-----------------------------------------");
		
		// for loop 네이밍 + break문 활용
		// for문의 별칭을 달고 break (별칭);을 통해 내부에서 외부 반복문을 종료시킬 수 있다.
		loop1 : for(int i = 0; i < 5; i++) { // 외부 반복문의 별칭 : loop1, 내부도 사용할수 있다.
			for(int j = 0; j < 5; j++) {
				if(i == 3) {
					break loop1;
				}
				System.out.println("\t i : " + i + ", j : " + j);
			}
			System.out.println("i : " + i);
		}
		System.out.println("-----------------------------------------");
		
		// flag 기법
		// - flag 변수를 활용하여 내부 반복문에서 종료하는 조건을 외부 반복문으로 전달하는 기법
		//  -> 고전적인 기법, 알고리즘적으로 깔끔하게 구현할수 있어서 선호하는 사람이 많다.
		
		boolean flag = false; // 종료 조건에 따라 true로 바꿀 flag 변수
//		int flag = 0; // int형으로 만들고 count 시키는 경우도 있다.
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(i * j == 9) {
					flag = true; // flag를 세우는 라인
					break;
				}
				System.out.println("\t i : " + i + ", j : " + j);
			}
			if(flag) {
				break;
			}
			System.out.println("i : " + i);
		}
		
	}

}













