package ex03.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// csv를 파싱하여 List<LineInfo>로 생성하는 파서
public class CSVParser {
	
	// path : 파일경로,
	// charset : 문자열 인코딩
	public static List<LineInfo> makeCSVList(String path, String charset) {
		List<LineInfo> list = new ArrayList<>();
		
		try (FileReader fr = new FileReader(path, Charset.forName(charset));
			 BufferedReader br = new BufferedReader(fr); ) {
			String headerStr = br.readLine();
			List<String> headerList = csvLineToList(headerStr);
			
			String str = null;
			while((str = br.readLine()) != null) {
				List<String> infoList = csvLineToList(str);
				if(infoList == null) {
					continue;
				}
				LineInfo info = new LineInfo(headerList, infoList);
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
//	headerStr : ﻿메뉴ID,식당ID,메뉴명,메뉴가격,메뉴태그정보,등록일시
//	line : 1,858991,짜장면,6000,"주재료 : 돼지고기,야채,면,밀가루 / 조리법 : 볶음 / 소스 : 춘장",2020-10-26 16:45
	private static List<String> csvLineToList(String csvLine) {
		try {
			String delemeter = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
			List<String> list = new ArrayList<>();
			String[] array = csvLine.split(delemeter);
			csvLine = csvLine.replace("\uFEFF", "");
			for(String str : array) {
//				if(str.contains("메뉴ID")) {
//					str = str.substring(1);
//				}
				str = str.replace("\"", "").strip();
				list.add(str);
			}
			return list;
		} catch (Exception e) {
			System.err.println("csvLine : " + csvLine);
			e.printStackTrace();
		}
		return null;
	}
}
















