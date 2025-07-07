package com.codeit.ex01.conditional;

import java.util.Scanner;

// 강사가 추천하는 if문 구성 스타일
// 1. 예외를 먼저 if + return문으로 필터링한다. (나중에는 예외로 처리하여 날린다.)
// 2. 정상 케이스를 가장 아래쪽으로 배치하고, 들여쓰기 0 level로 구성 하도록 한다.
// -> 해당 스타일의 장점 : 코드의 복잡도가 줄어든다. (간결해진다.)
public class IfStatement3 {
	// 로그인 기능
	// ID를 입력 받고 3글자 미만이면 "ID가 짧습니다." -> 실패
	// PW를 입력 받고 8글자 미만이면 실패, PW에 ID가 포함 되어 있으면 실패.
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("아이디를 입력하세요");
		String id = sc.nextLine();

		//예외 필터1
		if(id.length() < 3){
			System.out.println("아이디가 짧습니다.");
			return;
		}

		System.out.println("PW를 입력해주세요");
		String pw = sc.nextLine();
		//예외 필터2	-> 메서드 종료
		if(pw.length() < 8){
			System.out.println("PW가 너무 짧습니다.");
			return;
		}
		//예외 필터3
		if(pw.contains(id)){
			System.out.println("PW에 ID가 포함되어 있습니다.");
			return;
		}
		//합쳐도 됨
		// if(pw.length() < 8 || pw.contains(id)){
		// 	System.out.println("PW가 짧거나 ID가 포함되어있습니다.");
		// 	return;
		// }
		System.out.println("로그인 성공");
	}
}
