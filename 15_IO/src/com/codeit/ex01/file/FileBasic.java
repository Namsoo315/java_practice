package com.codeit.ex01.file;

// 파일 경로(Path)란?
//  - File이 저장되어 있는 보조기억장치(HDD, SSD)의 실제 물리적인 경로(위치)
//  - window의 계열은 대소문자를 구별하지 않지만, linux(mac)은 대소문자를 구별한다.

// 절대 경로 vs 상대 경로

// 1. 절대 경로 - 익숙한 것
// - Root(윈도우-'c:', 리눅스-'')로 부터 표기되는 절대적인 경로
// C:\dev_source\work_space_java\kb6_lecture\15_IO\java.txt
// c:\test\data.txt
// 빠르게 이스케이프 문자 적용하는 방법은 "" 안에 경로를 복사하는 것을 추천 ★★★★★

// 2. 상대 경로
// - 프로그램의 실행 위치(나)로부터 표기되는 상대적인 경로 '.'현재, '..'이전으로 표기되는 경로
// - os console 명령어로부터 파생된 개념
// - '.' : 현재 위치, '..' : 상위폴더(이전) 경로, './../../' 현재-상위-상위 경로
// - ex) './java.txt', 'java.txt', './../15_IO/java.txt'

import java.io.File;

public class FileBasic {
	public static void main(String[] args) {

		File file = new File("./");
		System.out.println(file.getAbsolutePath());    //절대경로
		File file1 = new File("./15_IO/java.txt");
		System.out.println(file1.getAbsolutePath());

		//절대경로로 읽어오기
		File file2 = new File(file1.getAbsolutePath());
		System.out.println(file2.getAbsolutePath());

		//상대, 절대 경로 출력
		System.out.println(file1.getPath());            //상대
		System.out.println(file1.getAbsolutePath());    //절대
		System.out.println("================================================");

		// 부모 (상위 폴더)
		System.out.println(file1.getParent());    //부모 경로
		System.out.println(file2.getParent());    //부모 경로
		System.out.println("================================================");

		System.out.println(file1.canRead());
		System.out.println(file1.canWrite());
		System.out.println(file1.canExecute());
		System.out.println("================================================");

		//권한 설정 (윈도우에서는 쓰기만 가능)
		boolean result = file1.setWritable(false);	//읽기 전용
		System.out.println(result + ", " + file1.canWrite());	//쓰기 못함

		boolean result2 = file1.setWritable(true);	//읽기 전용
		System.out.println(result2 + ", " + file1.canWrite());

	}
}
