package ex03.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		List<LineInfo> menuList = CSVParser.makeCSVList("20_ParsingCrawlingCsv/cdata/전라남도_메뉴정보_20210120.csv", "UTF-8");
		// System.out.println("메뉴 정보 출력하기");
		// menuList.forEach(System.out::println);
		System.out.println(
			"==========================================================================================");
		System.out.println(menuList.get(0).getData("메뉴명"));
		System.out.println(menuList.get(0).getData("메뉴가격"));
		System.out.println(
			"==========================================================================================");

		// 식당 리스트 파싱해오기
		List<LineInfo> restaurantList = CSVParser.makeCSVList("20_ParsingCrawlingCsv/cdata/전라남도_식당정보_20201229.csv",
			"EUC-KR");
		System.out.println("식당 정보 출력하기");
		// restaurantList.forEach(System.out::println);
		System.out.println(
			"==========================================================================================");
		System.out.println(restaurantList.get(0).getData("메뉴명"));
		System.out.println(restaurantList.get(0).getData("메뉴가격"));
		System.out.println(
			"==========================================================================================");

		// 1. 식당과 모든 메뉴를 탐색할 수 있는 기능
		// 1-1 무지성 접근 -> 성능적으로 최악이다.
		// for (LineInfo rInfo : restaurantList) {
		// 	String rId = rInfo.getData("식당ID");
		// 	for (LineInfo mInfo : menuList) {
		// 		if(mInfo.getData("식당ID").equals(rId)){
		// 			System.out.println("\t" + mInfo);
		// 		}
		// 	}
		// 	System.gc();
		// }
		//
		// System.out.println("--------------------------------------------------");

		// 1-2) 자료구조 사용 (map을 통한 시간 단축)

		// 식당 ID - 식당 LINE 정보
		Map<String, LineInfo> restaurantIdToLineInfoMap = new HashMap<>();

		// 식당 ID - 메뉴 리스트
		Map<String, List<LineInfo>> restaurantIdToMenuListMap = new HashMap<>();

		// 초기화 코드
		for (LineInfo lineInfo : restaurantList) {
			restaurantIdToLineInfoMap.put(lineInfo.getData("식당ID"), lineInfo);
		}

		for (LineInfo lineInfo : menuList) {
			String rid = lineInfo.getData("식당ID");
			List<LineInfo> list = restaurantIdToMenuListMap.get(rid);

			if (list == null) {
				list = new ArrayList<>();
				restaurantIdToMenuListMap.put(rid, list);
			}
			list.add(lineInfo);
		}

		for (LineInfo lineInfo : restaurantList) {
			String rid = lineInfo.getData("식당ID");
			System.out.println(lineInfo);
			List<LineInfo> list = restaurantIdToMenuListMap.get(rid);
			for (LineInfo lineInfo2 : list){
				System.out.println("\t" + lineInfo2);
			}
		}

		System.out.println("==========================================================================");

		// 2. 식당을 찾으면 메뉴까지 보여주는 기능
		String keyword = "갈비";

		for(LineInfo lineInfo : restaurantList){
			if(lineInfo.getData("식당명").contains(keyword)){
				System.out.println(lineInfo);
				List<LineInfo> list = restaurantIdToMenuListMap.get(lineInfo.getData("식당ID"));
				for(LineInfo lineInfo2 : list){
					System.out.println("==========================================================================");
					System.out.println("\t" + lineInfo2);
					System.out.println("==========================================================================");
				}
			}
		}

		// 3. 메뉴의 키워드를 찾으면 식당정보를 출력하는 기능
		String keyword2 = "닭갈비";
		for(LineInfo lineInfo : menuList){
			if(lineInfo.getData("메뉴명").contains(keyword2)){
				System.out.println("==========================================================================");
				System.out.println(lineInfo);
				String rid = lineInfo.getData("식당ID");
				LineInfo lineInfo2 = restaurantIdToLineInfoMap.get(rid);
				System.out.println(lineInfo2);
				System.out.println("==========================================================================");
			}
		}
	}
}
