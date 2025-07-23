package com.codeit.ex03.file_straem_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class FileInputStreamExam {
	public static void main(String[] args) {
		FileInputStream fis = null;

		try {
			// 오픈부
			// 1. 파일로 읽어오기
			// fis = new FileInputStream(new File("15_IO/test.txt"));
			// fis = new FileInputStream(Path.of("15_IO/test.txt").toFile());

			// 2. 경로로 읽어오기
			fis = new FileInputStream("15_IO/test.txt");
			// fis = new FileInputStream("15_IO/java.txt");		// 유니코드가 있어 깨진다!

			// 출력부 - 1byte씩 읽어오는 예제
			int read = 0;
			while ((read = fis.read()) != -1) {        // -1 = the end of file, EOF
				System.out.print((char)read);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
