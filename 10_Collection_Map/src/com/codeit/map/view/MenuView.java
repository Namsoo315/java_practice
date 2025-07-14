package com.codeit.map.view;

import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.codeit.map.controller.MemberController;
import com.codeit.map.model.Member;

public class MenuView {
	private final Scanner sc = new Scanner(System.in);
	private final MemberController mc = new MemberController();

	public void mainMenu() {

		System.out.println("========== Codeit 사이트 ==========");

		while (true) {
			System.out.println("");
			System.out.println("=====***** 메인 메뉴 *****=====");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 같은 이름 회원 찾기");
			System.out.println("9. 종료");
			System.out.print("메뉴 번호 입력 : ");

			int menuNum = Integer.parseInt(sc.nextLine());

			switch (menuNum) {
			case 1:
				joinMembership();
				continue;
			case 2:
				login();
				continue;
			case 3:
				sameName();
				continue;
			case 9:
				System.out.println("프로그램 종료.");
				return;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}

	public void memberMenu() {
		while (true) {
			System.out.println("*** 회원 메뉴 ***");
			System.out.println("1. 비밀번호 바꾸기");// changePassword() 실행
			System.out.println("2. 이름 바꾸기"); // changeName()
			System.out.println("3. 로그아웃"); // “로그아웃 되었습니다.” 출력 후 mainMenu()로 리턴
			System.out.print("메뉴 번호 선택 : "); // 입력 받음

			int menuNum = Integer.parseInt(sc.nextLine());

			switch (menuNum) {
			case 1:
				changePassword();
				continue;
			case 2:
				changeName();
				continue;
			case 3:
				System.out.println("로그아웃 되었습니다.");
				return;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
	}


	public void joinMembership() {
		System.out.print("아이디 : ");
		String id = sc.nextLine();

		System.out.print("비밀번호 : ");
		String password = sc.nextLine();

		System.out.print("이름 : ");
		String name = sc.nextLine();

		Member m = new Member(password, name);

		boolean result = mc.joinMembership(id, m);

		if (result) {
			System.out.println("성공적으로 회원가입을 완료하였습니다.");
			return;
		}

		System.out.println("중복된 ID입니다. 다시 시도해 주세요.");
	}

	public void login() {
		while (true) {
			System.out.print("아이디 : ");
			String id = sc.nextLine();

			System.out.print("비밀번호 : ");
			String password = sc.nextLine();

			String result = mc.login(id, password);
			if (result == null) {
				System.out.println("ID or PW를 잘못 입력하셨습니다. 다시 입력해 주세요.");
				continue;
			}
			System.out.println(result + "님, 환영합니다!");
			break;
		}
		memberMenu();
	}

	public void changePassword() {
		while (true) {
			System.out.print("아이디 : ");
			String id = sc.nextLine();

			System.out.print("현재 비밀번호 : ");
			String oldPw = sc.nextLine();

			System.out.print("새로운 비밀번호 : ");
			String newPw = sc.nextLine();

			boolean result = mc.changePassword(id, oldPw, newPw);
			if (result) {
				System.out.println("비밀번호 변경에 성공하셨습니다.");
				return;
			}
			System.out.println("비밀번호 변경에 실패하셨습니다. 다시 입력해 주세요");
		}
	}

	public void changeName() {
		while (true) {
			System.out.print("아이디 : ");
			String id = sc.nextLine();

			System.out.print("비밀번호 : ");
			String password = sc.nextLine();
			String result = mc.login(id, password);
			
			if (result == null) {
				System.out.println("이름 변경에 실패했습니다. 다시 입력해주세요.");
				continue;
			}
			
			System.out.println("현재 저장되어 있는 이름 : " + result);

			System.out.print("이름 : ");
			String newName = sc.nextLine();

			mc.changeName(id, newName);
			break;
		}
	}

	public void sameName() {
		System.out.print("이름 : ");
		String name = sc.nextLine();
		
		//검색할 이름을 받고 mc의 sameName()메소드로 넘김.
//		반환 값을 가지고 entrySet()을 이용하여 ‘이름-아이디’ 형식으로 출력

		TreeMap<String, Member> map = mc.sameName(name);
		Set<Entry<String, Member>> entrySet = map.entrySet();
		
		for(Entry<String, Member> entry : entrySet) {
			System.out.println(name +"-" + entry.getKey()+"," + entry.getValue());
		}
	}

}
