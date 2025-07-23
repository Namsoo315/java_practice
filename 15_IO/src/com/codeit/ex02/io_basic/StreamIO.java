package com.codeit.ex02.io_basic;

// Stream이란? - (한방향 빨대다!)
// - Binary(이진) 데이터를 다루는 Stream = 1byte씩 처리 가능
// - 활용 : 이미지나 동영상 파일, 네트워크, 프로그램간 데이터 전송 때

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

// IO 객체 사용시의 코딩 테크닉 - 고전 ver.
// 1. 가장 윗단에 IO 객체를 선언하고, null로 초기화한다. 이름은 앞글자를 줄여서 선언한다.
// 2. try-catch 문을 열고, e.printStackTrace 선언 및 finally 문 선언
// 3. IO 객체를 오픈한다. -> try안에서
// 4. 필요한 IO을 수행한다. 보통 while문으로 수행
// 5. finally에 사용한 IO객체'들'을 정리(close)한다.
// -> ※ try-with-resources문 활용 권장 ★★★★★
public class StreamIO {
	public static void main(String[] args) {

		//선언부
		InputStream is = null;
		OutputStream os = null;
		try {
			// 오픈부
			is = System.in;
			os = System.out;

			// 동작부
			System.out.println("숫자를 입력하세요.");
			int read = is.read();
			os.write(read);

			// os.flush();
			// write 이후에 반드시 필요한 문장
			// 1. close : 자원을 정리하면서 flush를 수행하고 스트림을 닫음.
			// 2. flush : write 데이터를 강제로 밀어내는 방법, 반드시 사용 권장.

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close 구현부
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
