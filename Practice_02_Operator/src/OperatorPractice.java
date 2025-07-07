import java.util.Scanner;

public class OperatorPractice {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		OperatorPractice op = new OperatorPractice();
		op.practice1();
		op.practice2();
		op.practice3();
		op.practice4();
	}

	public void practice1() {
		System.out.print("인원 수 :");
		int numPeople = sc.nextInt();
		System.out.print("사탕 개수 :");
		int candy = sc.nextInt();

		System.out.println("1인당 사탕 개수 " + (candy / numPeople));
		System.out.println("남는 사탕 개수 " + (candy % numPeople));
	}

	public void practice2() {
		System.out.print("정수 : ");
		int num = sc.nextInt();

		System.out.println(num > 0 ? "양수다." : "양수가 아니다.");

	}

	public void practice3() {
		System.out.print("정수 : ");
		int num = sc.nextInt();
		System.out.println(num > 0 ? "양수다." : num == 0 ? "0이다." : "음수다");
	}

	public void practice4() {
		System.out.print("정수 : ");
		int num = sc.nextInt();
		System.out.println(num % 2 == 0 ? "짝수다" : "홀수다");
	}
}
