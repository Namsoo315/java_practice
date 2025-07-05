package variablepractice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// 이름, 성별, 나이, 키를 사용자에게 입력 받아 각각의 값을 변수에 담고 출력하세요.
public class VariablePractice1 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		bw.write("이름을 입력하세요 :");
		String name = br.readLine();
		System.out.print("성별을 입력하세요(남/여) :");
		String gender = br.readLine();
		System.out.print("나이를 입력하세요 :");
		int age = Integer.parseInt(br.readLine());
		System.out.print("키를 입력하세요 :");
		double tall = Double.parseDouble(br.readLine());

		System.out.println("키 " + tall + "cm인 " + age + "살 " + gender
		+"자 " + name + "님 반갑습니다!");
	}
}
