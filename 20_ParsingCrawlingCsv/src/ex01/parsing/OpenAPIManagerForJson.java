package ex01.parsing;


// https://www.kobis.or.kr/kobisopenapi/homepg/apiservice/searchServiceInfo.do

import ex01.parsing.dto.BoxOffice;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// org.JSON 활용 예제
public class OpenAPIManagerForJson {
	public static final String KEY = "be34f20d99e875855b6997ecb0c02f27";
	public static final String WEEKLY_BOXOFFICE_JSON_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json";


	public static void main(String[] args) {
		List<BoxOffice> list = parse("20250801");
		for(BoxOffice b : list) {
			System.out.println(b.toString());
		}
	}

	public static List<BoxOffice> parse(String dateStr) {
		// URL 조합하는 단계
		StringBuilder urlBuilder = new StringBuilder(WEEKLY_BOXOFFICE_JSON_URL);
		urlBuilder.append("?key=").append(KEY);
		urlBuilder.append("&targetDt=").append(dateStr);
		System.out.println("url:" + urlBuilder.toString());

		// URL을 통해 HttpURLConnection 객체를 생성하고 실제 서버로 요청하는 단계
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			int code = conn.getResponseCode();
			System.out.println("Response code: " + code);
			// 요청이 실패했을때는 넘어감
			if (code != 200) {
				System.out.println("요청에 실패하였습니다!");
				return new ArrayList<>();
			}
			// 성공 케이스
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			//            System.out.println(sb.toString()); // 문자열

			// org.json을 통한 파싱
			JSONObject jsonObject = new JSONObject(sb.toString()); // 파싱 시작
			JSONObject childObj = jsonObject.getJSONObject("boxOfficeResult");
			System.out.println(childObj);
			System.out.println("boxofficeType : " + childObj.get("boxofficeType"));
			System.out.println("showRange : " + childObj.get("showRange"));
			System.out.println("yearWeekTime : " + childObj.get("yearWeekTime"));
			// weeklyBoxOfficeList 배열로 가져오기
			JSONArray array = childObj.getJSONArray("weeklyBoxOfficeList");

			List<BoxOffice> list = new ArrayList<BoxOffice>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Integer rnum = getIntData(obj, "rnum");
				Integer rank = getIntData(obj, "rank");
				Integer rankInten = getIntData(obj, "rankInten");
				String rankOldAndNew = getStrData(obj, "rankOldAndNew");
				String movieCd = getStrData(obj, "movieCd");
				String movieNm = getStrData(obj, "movieNm");
				String openDt = getStrData(obj, "openDt");
				Long salesAmt = getLongData(obj, "salesAmt");
				Double salesShare = getDoubleData(obj, "salesShare");
				Long salesInten = getLongData(obj, "salesInten");
				Double salesChange = getDoubleData(obj, "salesChange");
				Long salesAcc = getLongData(obj, "salesAcc");
				Long audiCnt = getLongData(obj, "audiCnt");
				Long audiInten = getLongData(obj, "audiInten");
				Double audiChange = getDoubleData(obj, "audiChange");
				Long audiAcc = getLongData(obj, "audiAcc");
				Long scrnCnt = getLongData(obj, "scrnCnt");
				Long showCnt = getLongData(obj, "showCnt");

				BoxOffice boxOffice = new BoxOffice(
					rnum, rank, rankInten, rankOldAndNew,
					movieCd, movieNm, openDt, salesAmt,
					salesShare, salesInten, salesChange, salesAcc,
					audiCnt, audiInten, audiChange, audiAcc,
					scrnCnt, showCnt
				);
				list.add(boxOffice);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private static String getStrData(JSONObject obj, String key) {
		try {
			return (String) obj.get(key);
		} catch (Exception e) {
			return "-"; // 없는 데이터는 null로 처리
		}
	}

	private static Long getLongData(JSONObject obj, String key) {
		try {
			String str = (String) obj.get(key);
			return Long.parseLong(str);
		} catch (Exception e) {
			return null;
		}
	}

	private static Integer getIntData(JSONObject obj, String key) {
		try {
			String str = (String) obj.get(key);
			return Integer.parseInt(str);
		} catch (Exception e) {
			return null;
		}
	}

	private static Double getDoubleData(JSONObject obj, String key) {
		try {
			String str = (String) obj.get(key);
			return Double.parseDouble(str);
		} catch (Exception e) {
			return null;
		}
	}


}