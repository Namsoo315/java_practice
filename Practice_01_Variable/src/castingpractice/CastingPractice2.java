package castingpractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 실수형으로 국어, 영어, 수학 세 과목의 점수를 입력 받아 총점과 평균을 출력하세요.
// 이 때 총점과 평균은 정수형으로 처리하세요.
public class CastingPractice2 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("국어 : ");
		double hangul = Double.parseDouble(br.readLine());
		System.out.print("영어 : ");
		double english = Double.parseDouble(br.readLine());
		System.out.print("수학 : ");
		double math = Double.parseDouble(br.readLine());

		double sum = hangul + english + math;
		double avg = sum / 3;
		System.out.println("총점 : " + sum);
		System.out.println("평균 : " + avg);
	}
}
