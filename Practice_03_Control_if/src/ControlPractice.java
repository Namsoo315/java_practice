import java.util.*;

public class ControlPractice {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		ControlPractice cp = new ControlPractice();
		cp.practice1();
		cp.practice2();
		cp.practice3();
		cp.practice4();
		cp.practice5();
		cp.practice6();
		cp.practice7();
		cp.practice8();
	}

	public void practice1() {
		System.out.println("정수 : ");
		int num = sc.nextInt();
		if (num > 0) {
			System.out.println("양수다.");
		} else {
			System.out.println("양수가 아니다.");
		}
	}

	public void practice2() {
		System.out.println("정수 : ");
		int num = sc.nextInt();
		if (num > 0) {
			System.out.println("양수다.");
		} else if (num == 0) {
			System.out.println("0이다.");
		} else {
			System.out.println("음수다.");
		}
	}

	public void practice3() {
		System.out.println("정수 : ");
		int num = sc.nextInt();
		if (num % 2 == 0) {
			System.out.println("짝수다.");
		} else {
			System.out.println("홀수다");
		}
	}

	public void practice4() {
		System.out.println("정수 : ");
		int num = sc.nextInt();
		if (num <= 0) {
			System.out.println("양수만 입력해주세요.");
		} else if (num % 2 == 0) {
			System.out.println("짝수다.");
		} else {
			System.out.println("홀수다");
		}
	}

	public void practice5() {
		System.out.println("주빈번호를 입력하세요 (- 포함) : ");
		String RRN = sc.nextLine();

		if (RRN.contains("-2")) {
			System.out.println("여자");
		} else {
			System.out.println("남자");
		}
	}

	public void practice6() {

		System.out.print("나이 : ");
		int age = sc.nextInt();

		if (age > 19) {
			System.out.println("성인");
		} else if (age > 13) {
			System.out.println("청소년");
		} else {
			System.out.println("어린이");
		}

	}

	public void practice7() {
		System.out.print("아이디 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호 : ");
		String pw = sc.nextLine();

		if (id.equals("test")) {
			if (pw.equals("1234")) {
				System.out.println("로그인 성공");
			} else {
				System.out.println("비밀번호가 틀렸습니다.");
			}
		} else {
			System.out.println("아이디가 틀렸습니다.");
		}

	}

	public void practice8() {
		System.out.print("피연산자1 입력 : ");
		int num1 = sc.nextInt();
		System.out.print("피연산자2 입력 : ");
		int num2 = sc.nextInt();
		System.out.print("연산자 입력 (+,-,*,/,%) :");
		char cul = sc.next().charAt(0);

		if (num1 <= 0 && num2 <= 0) {
			System.out.println("잘못입력하셨습니다. 프로그램을 종료합니다");
			return;
		}
		double sum = 0;
		switch (cul) {
			case '+':
				sum = num1 + num2;
				break;
			case '-':
				sum = num1 - num2;
				break;
			case '*':
				sum = num1 * num2;
				break;
			case '/':
				sum = (double)num1 / (double)num2;
				break;
			case '%':
				sum = num1 % num2;
				break;
			default:
				System.out.println("잘못입력하셨습니다. 프로그램을 종료합니다.");
				return;
		}

		System.out.println(num1 + " " + cul + " " + num2 + " = " + sum);

	}
}