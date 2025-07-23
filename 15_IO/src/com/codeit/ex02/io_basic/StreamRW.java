package com.codeit.ex02.io_basic;

// reader : 문자열 단위로 읽어오는 기능
// writer : 문자열 단위로 쓰는 기능
// java에서 문자열은 2byte를 기준으로 함(unicode 기준)

// try-with-resources문  ★★★★★★
// - java 1.7부터 추가된 문법으로 Closeable 객체의 close를 자동으로 해주는 문법
// - try (open 부) {~} catch(e){~}로 활용하는데, open부에서 자원생성을 수행해야한다.
// - ※ 주의 : Closeable 객체만 활용 가능!

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// IO 객체 사용시의 코딩 테크닉 - try-with-resources ver. (java 1.7 이상)
// 1. try-catch문을 생성한다.
// 2. try 뒤에 ()를 열고 IO 객체 선언 및 스트림 오픈을 수행한다.
// 3. 필요한 IO을 수행한다. 보통 while문으로 수행
//  -> try-with-resources을 사용하면 finally 구현이 필요 없다.
public class StreamRW {
	public static void main(String[] args) {
		try (InputStreamReader isr = new InputStreamReader(System.in);
			 OutputStreamWriter osw = new OutputStreamWriter(System.out);
			 PrintWriter pw = new PrintWriter(osw);) {
			// System.out.println(); 밑에 꺼와 똑같음.
			pw.println("문자열을 입력해주세요");
			pw.flush();        // 버퍼에 있는 내용을 출력.

			char[] buf = new char[1024];    //buf 사이즈는 2의 배수이다.
			int size = isr.read(buf);    //1024 길이의 char을 읽어오기
			// read : Blocking 함수, 프로그램의 동작을 멈추고 특정 이벤트가 발생할 때 다시 프로그램 흐름이 실행되는 함수.

			pw.println("read size : " + size);
			// pw.println("input : " + buf);			input : [C@3b07d329
			osw.append(new String(buf, 0, size));
			pw.println("input : " + new String(buf, 0, size));
			// pw.println("input : " + new String(buf));
			// pw.println("input : " + String.valueOf(buf));

			pw.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// close부는 생략!
		try {

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

		}
	}

}
