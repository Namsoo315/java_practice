package ex01.parsing;


// https://www.kobis.or.kr/kobisopenapi/homepg/apiservice/searchServiceInfo.do

import ex01.parsing.dto.BoxOffice;
import ex01.parsing.dto.BoxOfficeResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// org.JSON 활용 예제
public class OpenAPIManagerForJsonJackson {
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
			List<BoxOffice> list = new ArrayList<>();

			// Jackson을 통한 파싱
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(sb.toString());
			JsonNode boxofficeResult = root.get("boxOfficeResult");
			BoxOfficeResult result = objectMapper.readValue(boxofficeResult.toString(), BoxOfficeResult.class);
			System.out.println(result.toString());

			//순수 list 만 가져오기
			JsonNode weeklyBoxOfficeList = root.path("boxOfficeResult").path("weeklyBoxOfficeList");
			list = objectMapper.readerForListOf(BoxOffice.class).readValue(weeklyBoxOfficeList.toString());
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}