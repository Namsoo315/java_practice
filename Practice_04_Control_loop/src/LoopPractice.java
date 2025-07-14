import java.util.Scanner;

public class LoopPractice {
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		LoopPractice lp = new LoopPractice();
		lp.practice1();
		lp.practice2();
		lp.practice3();
		lp.practice4();
		lp.practice5();
		lp.practice6();
		lp.practice7();
		lp.practice8();
		lp.practice9();
	}

	public void practice1() {
		System.out.print("1이상의 숫자를 입력하세요 :");
		int len = sc.nextInt();

		if (len == 0) {
			System.out.println("1이상의 숫자를 입력하세요");
		}

		for (int i = 1; i <= len; i++) {
			System.out.print(i + " ");
		}
	}

	public void practice2() {
		System.out.print("1이상의 숫자를 입력하세요 :");
		int len = sc.nextInt();

		if (len == 0) {
			System.out.println("1이상의 숫자를 입력하세요");
		}

		for (int i = len; i >= 1; i--) {
			System.out.print(i + " ");
		}
	}

	public void practice3() {
		System.out.print("정수를 하나 입력하세요 : ");
		int num = sc.nextInt();
		int sum = num;
		for (int i = 1; i < num; i++) {
			System.out.print(i + " + ");
			sum += i;
		}
		System.out.print(num + " = " + sum);
	}

	public void practice4() {
		System.out.print("첫 번째 숫자 : ");
		int num1 = sc.nextInt();
		System.out.print("두 번째 숫자 : ");
		int num2 = sc.nextInt();
		if (num1 <= 0 || num2 <= 0) {
			System.out.println("1 이상의 숫자를 입력해주세요");
		} else if (num1 >= num2) {
			for (int i = num2; i <= num1; i++) {
				System.out.print(i + " ");
			}
		} else {
			for (int i = num1; i <= num2; i++) {
				System.out.print(i + " ");
			}
		}
	}

	public void practice5() {
		System.out.print("숫자 : ");
		int num = sc.nextInt();

		System.out.println("===== " + num + "단" + " =====");
		for (int i = 1; i <= 9; i++) {
			System.out.println(num + " * " + i + " = " + num * i);
		}
	}

	public void practice6() {
		System.out.print("숫자 : ");
		int num = sc.nextInt();
		if (num > 9) {
			System.out.println("9이하의 숫자만 입력해주세요.");
			return;
		}

		for (int i = num; i <= 9; i++) {
			System.out.println("===== " + i + "단" + " =====");
			for (int j = 1; j <= 9; j++) {
				System.out.println(i + " * " + j + " = " + i * j);
			}
		}
	}

	public void practice7() {
		System.out.print("시작 숫자 : ");
		int start_num = sc.nextInt();
		System.out.print("공차 : ");
		int tolerance = sc.nextInt();

		for (int i = 0; i < 10; i++) {
			System.out.print(start_num + " ");
			start_num += tolerance;
		}
	}

	public void practice8() {
		System.out.print("정수 입력 : ");
		int num = sc.nextInt();
		for(int i = 1; i <= num; i++){
			for(int j = 1; j <= i; j++){
				System.out.print("*");
			}
			System.out.println();
		}
	}

	public void practice9() {
		System.out.print("정수 입력 : ");
		int num = sc.nextInt();
		for(int i = 1; i <= num; i++){
			for(int j = num; j >= i; j--){
				System.out.print("*");
			}
			System.out.println();
		}
	}

}