package ex02.crawling;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupEXam {
	public static void main(String[] args) throws IOException {
		// 다음 뉴스 페이지 크롤링
		String URL = "https://news.daum.net/tech/";

		Connection conn = Jsoup.connect(URL);
		Document doc = conn.get(); //정해진 url로 부터 Document(Html 문서)를 가져오는 방법.

		// Elements와 Element를 구해와야함.
		// Elements : 태그 배열로 구성된 객체
		// Elemnet : 태그 하나만 의미
		// doc.getElementById();		// 단일
		// doc.getElementsByTag();		// 배열

		// 1. DOC.getElementsByTag()
		// getElementsByClass : class의 이름으로 크롤링
		Elements elements = doc.getElementsByTag("a");	// a 태그 모두 가져오기
		for (Element element : elements) {
			Elements newsHeadLine = element.getElementsByClass("item_newsheadline2");
			if(!newsHeadLine.isEmpty()){
				System.out.println("text : "+ newsHeadLine.text());
				System.out.println("href : "+ newsHeadLine.attr("href"));
			}
		}
		System.out.println("-----------------------------------------------");

		// 2. class + 부모 자식 접근해보기
		Element newsHeadLineFirst1 = doc.getElementsByClass("list_newsheadline2").first();
		Element newHeadLineList = newsHeadLineFirst1.firstElementChild();
		System.out.println(newsHeadLineFirst1);
		System.out.println("===================================================================");
		System.out.println(newHeadLineList);
		System.out.println("===================================================================");

		Elements newsList = newsHeadLineFirst1.getElementsByClass("item_newsheadline2");
		for (Element element : newsList) {
			Element title = element.getElementsByClass("tit_txt").first();	// 타이틀

			Element desc = null;
			if(!element.getElementsByClass("desc_txt").isEmpty()){
				desc = element.getElementsByClass("desc_txt").first();	// 설명
			}
			Element press = element.getElementsByClass("txt_info").first();	// 언론사

			System.out.println("제목 : " + title.text());
			System.out.println("설명 : " + (desc == null ? null : desc.toString()));
			System.out.println("언론사 : " + press.text());
			System.out.println("링크 : "+ element.attr("href"));
		}

		System.out.println("============================================================");

		// -----------------------------
		// 3. CSS Query로 접근 (class + 태그)
		// -----------------------------
		Elements cssNewsList = doc.select(".list_newsheadline2 a.item_newsheadline2");
		for (Element element : cssNewsList) {
			Element titleElement = element.selectFirst(".tit_txt");
			Element pressElement = element.selectFirst(".txt_info");
			System.out.println("제목 : " + titleElement.text());
			System.out.println("링크 : " + element.attr("href"));
			System.out.println("작성자 : " + (pressElement != null ? pressElement.text() : ""));
			System.out.println();
		}
	}
}
