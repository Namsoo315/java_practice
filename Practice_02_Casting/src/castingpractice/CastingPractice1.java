package castingpractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 키보드로 문자 하나를 입력 받아 그 문자의 유니코드를 출력하세요.
public class CastingPractice1 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("문자 : ");
		char c1 = br.readLine().charAt(0);
		System.out.print( c1 + " unicode : "+ (int)c1);
	}
}
