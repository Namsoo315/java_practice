package com.codeit.ex03.file_straem_io;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileReaderExam {

    // FileReader : 문자열을 읽어오는 reader, 문자열 인코딩★을 정할수 있다.
    public static void main(String[] args) throws IOException {
        FileReader fr = null;

        try {
//            fr = new FileReader("15_IO/test.txt", StandardCharsets.UTF_8);
//            fr = new FileReader("15_IO/java.txt"); // 읽기 성공 이유 : 프로젝트 인코딩이 마침 utf-8이라서 성공!!
//            fr = new FileReader("15_IO/java_UTF16.txt"); // 읽기 실패 이유 : 인코딩이 맞지 않아서, UTF-16으로 읽어와야함
            fr = new FileReader("15_IO/java_UTF16.txt", Charset.forName("UTF-16")); // 읽기 성공!, 인코딩 변경 성공!
            fr = new FileReader("15_IO/java_UTF16.txt", StandardCharsets.UTF_16); // 읽기 성공!
            // FileReader(String fileName, Charset charset)
            // charset을 지정하는 생성자로 활용해야한다!! ★★★★★
            // BOM : Byte order mark로 인코딩을 파일 앞부분에 표기하는 방법
            // -> 개발자가 인지 못하는 부분으로 파일을 생성할때 인코딩에 따라 text가 자동으로 붙는다.
            // -> 가끔은 수동으로 써야 txt 에디터에서 읽어올수 있다.
            // -> BOM가 이슈가 없도록 UTF-8로 맞추는것이 업계 표준!!! ★★★★★

            // buffer로 읽어오기
            char[] buf = new char[1024];
            while(fr.read(buf) != -1) { // EOF까지 읽는다.
                System.out.println(String.valueOf(buf).trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fr != null){
                fr.close();
            }
        }
    }
}
