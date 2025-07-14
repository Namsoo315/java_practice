package com.codeit.view;

import java.util.Scanner;

import com.codeit.controller.MemberController;
import com.codeit.model.Member;

public class MenuView {
	private final Scanner sc = new Scanner(System.in);
	private final MemberController mc = new MemberController();
	private char aChar;

	public void mainMenu() {
		while (true) {
			System.out.println("최대 등록 가능한 회원 수는 " + MemberController.MAX_SIZE + "명입니다.");
			System.out.println("현재 등록된 회원 수는 " + mc.existMemberNum() + "명입니다.");
			if (mc.existMemberNum() == MemberController.MAX_SIZE) {
				System.out.print(
					"1. 회원 등록(현재 인원 10명으로 가입 불가)\n2. 회원 검색\n3. 회원 정보 수정\n4. 회원 삭제\n5. 모두 출력\n9. 끝내기\n메뉴 번호 : ");
			} else {
				System.out.print("1. 회원 등록\n2. 회원 검색\n3. 회원 정보 수정\n4. 회원 삭제\n5. 모두 출력\n9. 끝내기\n메뉴 번호 : ");
			}
			int menuNum = Integer.parseInt(sc.nextLine());
			switch (menuNum) {
				case 1:
					insertMember();
					break;
				case 2:
					searchMember();
					break;
				case 3:
					updateMember();
					break;
				case 4:
					deleteMember();
					break;
				case 5:
					printAll();
					break;
				case 9:
					return;
				default:
					System.out.println("잘못 입력하였습니다.");
					break;
			}
		}
	}

	public void insertMember() {
		if (mc.existMemberNum() == MemberController.MAX_SIZE) {
			System.out.println("현재 등록 인원은 10명이 넘습니다.");
			return;
		}
		System.out.println("새 회원을 등록합니다.");

		String id = null;
		while (true) {
			System.out.print("아이디 : ");
			id = sc.nextLine();

			if (mc.checkId(id)) {
				System.out.println("중복된 아이디입니다. 다시 입력해주세요.");
				continue;
			} else {
				break;
			}
		}

		System.out.print("이름 : ");
		String name = sc.nextLine();

		System.out.print("비밀번호 : ");
		String password = sc.nextLine();

		System.out.print("이메일 : ");
		String email = sc.nextLine();

		char gender = 0;
		while (true) {
			System.out.print("성별(M/F) : ");
			gender = sc.nextLine().charAt(0);
			if (gender == 'M' || gender == 'F' || gender == 'm' || gender == 'f') {
				// aChar; // char 대문자 바꾸기
				break;
			} else {
				System.out.println("성별을 다시 입력해주세요.");
				continue;
			}
		}

		int age = 0;
		while (true) {
			System.out.print("나이 : ");
			String ageStr = sc.nextLine();
			try {
				age = Integer.parseInt(ageStr);
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
				continue;
			}
			break;
		}

		Member m = new Member(id, name, password, email, gender, age);
		mc.insertMember(m);
	}

	public void searchMember() {
		while (true) {
			System.out.print("\n1. 아이디로 검색하기\n2. 이름으로 검색하기\n3. 이메일로 검색하기\n9. 메인으로 돌아가기\n메뉴 번호 : ");
			int menuNum = Integer.parseInt(sc.nextLine());
			switch (menuNum) {
				case 1:
					searchId();
					break;
				case 2:
					searchName();
					break;
				case 3:
					searchEmail();
					break;
				case 9:
					System.out.println("메인으로 돌아갑니다.");
					return;
				default:
					System.out.println("잘못 입력하였습니다.");
					continue;
			}
			System.out.println();
			//			break;
		}
	}

	public void searchId() {
		System.out.print("검색 할 아이디 : ");
		String id = sc.nextLine();
		Member m = mc.searchId(id);

		if (m == null) {
			System.out.println("검색 결과가 없습니다");
		} else {
			System.out.println("찾으신 회원 결과 조회 입니다.");
			System.out.println(m.toString());
		}
	}

	public void searchName() {
		System.out.print("검색 할 이름 : ");
		String name = sc.nextLine();
		Member[] array = mc.searchName(name);

		if (array.length == 0) {
			System.out.println("검색 결과가 없습니다");
		} else {
			System.out.println("찾으신 회원 결과 조회 입니다.");
			for (Member m : array) {
				System.out.println(m.toString());
			}
		}
	}

	public void searchEmail() {
		System.out.print("검색 할 이메일 : ");
		String email = sc.nextLine();
		Member[] array = mc.searchEmail(email);

		if (array.length == 0) {
			System.out.println("검색 결과가 없습니다");
		} else {
			System.out.println("찾으신 회원 결과 조회 입니다.");
			for (Member m : array) {
				System.out.println(m.toString());
			}
		}
	}

	public void updateMember() {
		while (true) {
			System.out.print("1. 비밀번호 수정하기\n2. 이름 수정하기\n3. 이메일 수정하기\n9. 메인으로 돌아가기\n메뉴 선택 : ");
			int menuNum = Integer.parseInt(sc.nextLine());
			switch (menuNum) {
				case 1:
					updatePassword();
					break;
				case 2:
					updateName();
					break;
				case 3:
					updateEmail();
					break;
				case 9:
					System.out.println("메인으로 돌아갑니다.");
					return;
				default:
					System.out.println("잘못 입력하였습니다.");
					continue;
			}
			System.out.println();
			//			break;
		}
	}

	public void updatePassword() {
		System.out.print("수정할 회원의 아이디 : ");
		String id = sc.nextLine();
		System.out.print("수정할 비밀번호 : ");
		String password = sc.nextLine();

		boolean result = mc.updatePassword(id, password);

		if (result) {
			System.out.println("수정이 성공적으로 되었습니다.");
		} else {
			System.out.println("존재하지 않는 아이디 입니다.");
		}
	}

	public void updateName() {
		System.out.print("수정할 회원의 아이디 : ");
		String id = sc.nextLine();
		System.out.print("수정할 이름 : ");
		String name = sc.nextLine();

		boolean result = mc.updateName(id, name);

		if (result) {
			System.out.println("수정이 성공적으로 되었습니다.");
		} else {
			System.out.println("존재하지 않는 아이디 입니다.");
		}
	}

	public void updateEmail() {
		System.out.print("수정할 회원의 아이디 : ");
		String id = sc.nextLine();
		System.out.print("수정할 이메일 : ");
		String email = sc.nextLine();

		boolean result = mc.updateEmail(id, email);

		if (result) {
			System.out.println("수정이 성공적으로 되었습니다.");
		} else {
			System.out.println("존재하지 않는 아이디 입니다.");
		}
	}

	public void deleteMember() {
		while (true) {
			System.out.print("1. 특정 회원 삭제하기\n2. 모든 회원 삭제하기\n9. 메인으로 돌아가기\n메뉴 번호 : ");
			int menuNum = Integer.parseInt(sc.nextLine());
			switch (menuNum) {
				case 1:
					deleteOne();
					break;
				case 2:
					deleteAll();
					break;
				case 9:
					System.out.println("메인으로 돌아갑니다.");
					return;
				default:
					System.out.println("잘못 입력하였습니다.");
					continue;
			}
			System.out.println();
			//			break;
		}
	}

	public void deleteOne() {
		System.out.print("삭제할 회원의 아이디 : ");
		String id = sc.nextLine();
		System.out.print("정말 삭제하시겠습니까?(y/n)");
		String result = sc.nextLine();
		if (result.equalsIgnoreCase("Y")) {
			boolean result2 = mc.delete(id);
			if (result2) {
				System.out.println("삭제 되었습니다.");
			} else {
				System.out.println("실패하였습니다.");
			}
		} else {
			System.out.println("삭제가 취소 되었습니다.");
		}
		//		삭제할 회원의 아이디 : test1
		//		정말 삭제하시겠습니까?(y/n) : y
		//		성공적으로 삭제하였습니다.

	}

	public void deleteAll() {
		System.out.print("정말 삭제하시겠습니까?(y/n)");
		String result = sc.nextLine();
		if (result.equalsIgnoreCase("Y")) {
			mc.delete();
			System.out.println("삭제 되었습니다.");
		} else {
			System.out.println("삭제가 취소 되었습니다.");
		}
	}

	public void printAll() {
		if (mc.existMemberNum() == 0) {
			System.out.println("저장된 회원이 없습니다.");
			return;
		}

		Member[] array = mc.printAll();
		for (Member m : array) {
			if (m == null) {
				continue;
			}
			System.out.println(m.toString());
		}
		System.out.println();
	}

}
