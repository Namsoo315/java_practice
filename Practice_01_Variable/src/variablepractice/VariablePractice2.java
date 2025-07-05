package variablepractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 키보드로 정수 두 개를 입력 받아 두 수의 합, 차, 곱, 나누기한 몫을 출력하세요.
public class VariablePractice2 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("첫 번째 정수 : ");
		int a = Integer.parseInt(br.readLine());
		System.out.print("두 번째 정수 : ");
		int b = Integer.parseInt(br.readLine());
		System.out.println("더하기 결과 : " + (a + b));
		System.out.println("빼기 결과 : " + (a - b));
		System.out.println("곱하기 결과 : " + (a * b));
		System.out.println("나누기 결과 : " + (a / b));
	}
}
