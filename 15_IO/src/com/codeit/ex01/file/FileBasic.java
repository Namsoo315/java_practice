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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

		//파일 이름 변경 + 경로 변경 가능
		// File renamed = new File("./java2.txt");
		// boolean result3 = file1.renameTo(renamed);
		// System.out.println(result3 + ", " + file2.canWrite());

		// 수정 시간 출력
		System.out.println(file1.lastModified());
		Date date = new Date();

		//파일 길이
		System.out.println(file1.length() + " byte");
		System.out.println(file1.length() / 1024 + " Kbyte");
		System.out.println(file1.length() / 1024.0 / 1024.0 + " Mbyte");

		// 파일 삭제
		// file1.delete();

		//디렉토리 출력하기
		File directory = new File("./15_IO");
		if(directory.isDirectory()) {
			System.out.println("디렉토리 입니다.");
		}

		//디렉토리 파일들 보기
		System.out.println(Arrays.toString(directory.listFiles()));
		try {
			List<Path> list = Files.list(Path.of("./15_IO")).toList();
			System.out.println(list);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		//디렉토리 생성 ★
		//		mkdirs() : 입력된 경로에 폴더가 없으면 모두 생성  ★
		// 		mkdir() : 입력된 경로에 폴더만 생성
		File directory1 = new File("./15_IO/test/test2");
		boolean mkdir = directory1.mkdir();
		System.out.println(mkdir);
		boolean mkdirs = directory1.mkdirs();
		System.out.println(mkdirs);

		Path path = Path.of("./IO_15/test/test2/test3");
		// Files.createDirectories(path, null);		있다는것만 알고있자.
	}
}
