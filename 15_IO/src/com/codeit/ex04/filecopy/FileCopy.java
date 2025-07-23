package com.codeit.ex04.filecopy;

import java.io.*;

public class FileCopy {
	// File IO 시 Close문이 생략된 버전으로 작성 (Close를 자동으로 해줌)
	// Try-catch resource 버전 문법, 자바7 이후 부터 가능

	// Stream은 binary 파일을 다룰때 사용하는 객체류
	// 파일복사할때 완벽하게 원본을 복사할수 있다. 
	// 1byte씩 복사하는 고전적인 방법 = 가장 느린 방법!
	public static int fileCopyStreamVer(File source, File dest) {
		int size = 0; // 파일 사이즈
		// Try-catch resource : close를 하지 않아도 된다. Closeable를 상속받은 객체만 활용가능!!
		try (FileInputStream fis = new FileInputStream(source); 
				FileOutputStream fos = new FileOutputStream(dest);) {
			int read = 0;
			while((read = fis.read()) != -1 ) {
				fos.write(read);
				size++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // 에러일 경우 -1, 에러 설계!
		}
		return size;
	}
	
	// Reader / Writer : 문자열을 다루는 객체류, 복사할 경우 인코딩이 맞지 않으면 제대로 복사되지 않는다!!
	// 아래 코드는 만들면 안되는 코드였다!
	public static int fileCopyReadWriteVer(File source, File dest) {
		int size = 0; // 문자열의 갯수

		try(FileReader fr = new FileReader(source);
				FileWriter fw = new FileWriter(dest);
				) {
			int read = 0;
			while((read = fr.read()) != -1) {
				fw.write(read);
				size++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		return size;
	}
	
	public static void main(String[] args) {
		File source1 = new File("15_IO/java.txt");
		File dest1 = new File("15_IO/copy1_covid.txt");
		File dest2 = new File("15_IO/copy2_covid.txt");
		
		int result = 0;
		result = fileCopyStreamVer(source1, dest1);
		System.out.println("result1 : " + result); // 성공!
		result = fileCopyReadWriteVer(source1, dest2);
		System.out.println("result2 : " + result); // 성공!
		
		
		// UTF-16 복사
		File source2 = new File("15_IO/java_UTF16.txt");
		File dest3 = new File("15_IO/copy3_covid_UTF16.txt");
		File dest4 = new File("15_IO/copy4_covid_UTF16.txt");
		
		result = fileCopyStreamVer(source2, dest3); 
		System.out.println("result3 : " + result); // 성공!
		result = fileCopyReadWriteVer(source2, dest4); 
		System.out.println("result4 : " + result); // 실패! 이유 : 인코딩이 깨졌다.
		
		
		// image 파일 (binary 파일) 복사
		File source3 = new File("15_IO/image.jpg");
		File dest5 = new File("15_IO/copy5_image.jpg");
		File dest6 = new File("15_IO/copy6_image.jpg");
		
		result = fileCopyStreamVer(source3, dest5); 
		System.out.println("result5 : " + result); // 성공!
		result = fileCopyReadWriteVer(source3, dest6);
		System.out.println("result6 : " + result); // 복사 실패! 강아지 사라짐!

		// 결론! : Reader, Writer로는 파일 복사가 불가능하다.
		//        파일 복사할때는 반드시 Stream류를 활용해야한다!
		
	}
}
