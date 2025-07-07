package com.codeit.ex03.branch;

import java.util.Random;
import java.util.Scanner;

public class BranchStatement3 {
	public static void main(String[] args) {
		// 간단한 게임 예제,
		// 컴퓨터가 숫자 1 ~ 10까지 랜덤으로 숫자 하나를 뽑고,
		// 사람이 5번까지 맞추면 이기는 게임

		Scanner sc = new Scanner(System.in);
		
//		int randomInt = new Random().nextInt(10); // 0 ~ 9까지 랜덤으로 가져올때 -> 문제에선 틀린 값!
		// 0 1 2 3 4 5 6 7 8 9
		
		int randomInt = new Random().nextInt(10) + 1; // 1 ~ 10까지 랜덤한 숫자가 뽑힌다.
//		System.out.println(randomInt); // 랜덤숫자 출력하는 곳
		// 1 2 3 4 5 6 7 8 9 10
		
		char randomChar = (char)('A' + new Random().nextInt(5)); // 램덤 문자열 뽑을때!
//		System.out.println(randomChar); // 랜덤문자열 출력하는 곳
		
		// 0 ~ 10
//		int randomInt = new Random().nextInt(11); // ? -> 넥슨 사태가 일어난다!! 확률조작...
		
		int i = 5; // 기회 5번!
		boolean isWin = false; // flag = 깃발

		while(i > 0) { // 5 4 3 2 1 ...
			System.out.println("1 ~ 10 숫자 하나를 선택해주세요!");
//			int pickNum = sc.nextInt();
			int pickNum = Integer.parseInt(sc.nextLine());
			
			if(pickNum < 1 || pickNum > 10) {
				System.out.println("잘못된 숫자가 입력되었습니다. 다시 선택해주세요. : " + pickNum);
				continue; // count를 감소시키지 않고 기회 더준다!
			}
			if(pickNum == randomInt) {
				isWin = true;
				break;
			} else {
				System.out.println("틀렸습니다! " + (i-1) + "번 남았습니다.");
			}
			i--;
		}
		
		if(isWin) {
			System.out.println("맞췄습니다. 당신이 승리하였습니다.");
		}else {
			System.out.println("당신은 루저입니다!");
		}
		
		System.out.println("선택 된 숫자 : " + randomInt);
	}
}















