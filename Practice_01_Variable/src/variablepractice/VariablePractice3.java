package variablepractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 키보드로 가로, 세로 값을 값을 실수형으로 입력 받아 사각형의 면적과 둘레를 계산하여 출력하세요.
public class VariablePractice3 {
	public static void main(String[] args) throws IOException {
		BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
		System.out.print("가로 : ");
		double width = Double.parseDouble(br.readLine());
		System.out.print("세로 : ");
		double length = Double.parseDouble(br.readLine());

		double area = width * length;
		double perimeter = (width + length) * 2;

		System.out.println("면적 : " + area);
		System.out.println("둘레 : " + perimeter);
	}
}
