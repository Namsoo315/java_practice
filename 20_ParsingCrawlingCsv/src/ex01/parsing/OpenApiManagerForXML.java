package ex01.parsing;

import ex01.parsing.dto.BoxOffice;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OpenApiManagerForXML {
    public static final String KEY = "be34f20d99e875855b6997ecb0c02f27";
    public static final String WEEKLY_BOXOFFICE_XML_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.xml";


    public static void main(String[] args) {
        System.out.println("OpenApiManagerForXML");
        List<BoxOffice> list = parse("20250801");
        for (BoxOffice b : list) {
            System.out.println(b.toString());
        }
    }

    public static List<BoxOffice> parse(String dateStr) {
        List<BoxOffice> list = new ArrayList<>();

        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(WEEKLY_BOXOFFICE_XML_URL);
        urlBuffer.append("?" + "key=" + KEY); // 첫 번째만 물음표로 설정
        urlBuffer.append("&" + "targetDt=" + dateStr);
        urlBuffer.append("&" + "weekGb=" + "0");

        System.out.println(urlBuffer);

        try {
            URL url = new URL(urlBuffer.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            int code = conn.getResponseCode(); // 실제 호출하는 부
            System.out.println("ResponseCode : " + code);

            if (code < 200 || code > 300) {
                System.out.println("페이지가 잘못되었습니다.");
                return null;
            }

            // 3. 페이지 Parsing(해석)
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(conn.getInputStream()); // xml 부를 파싱하여 객체화

            doc.getDocumentElement().normalize();

            System.out.println("Root Element : " + doc.getDocumentElement().getNodeName());
            System.out.println("======================================================");

            String boxofficeType = doc.getElementsByTagName("boxofficeType").item(0).getTextContent();
            String showRange = doc.getElementsByTagName("showRange").item(0).getTextContent();
            String yearWeekTime = doc.getElementsByTagName("yearWeekTime").item(0).getTextContent();

            NodeList nList = doc.getElementsByTagName("weeklyBoxOffice");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                System.out.println("\nCurrent Element : " + node.getNodeName());
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        Element eElement = (Element) node;
                        System.out.println("rank    : " + getStrData(eElement, "rank"));
                        System.out.println("movieNm : " + getStrData(eElement, "movieNm"));
                        System.out.println("openDt  : " + getStrData(eElement, "openDt"));
                        System.out.println("audiAcc : " + getStrData(eElement, "audiAcc"));

                        int rnum = getIntData(eElement, "rnum");
                        int rank = getIntData(eElement, "rank");
                        int rankInten = getIntData(eElement, "rankInten");
                        String rankOldAndNew = getStrData(eElement, "rankOldAndNew");
                        String movieCd = getStrData(eElement, "movieCd");
                        String movieNm = getStrData(eElement, "movieNm");
                        String openDt = getStrData(eElement, "openDt");
                        long salesAmt = getLongData(eElement, "salesAmt");
                        double salesShare = getDoubleData(eElement, "salesShare");
                        long salesInten = getLongData(eElement, "salesInten");
                        double salesChange = getDoubleData(eElement, "salesChange");
                        long salesAcc = getLongData(eElement, "salesAcc");
                        long audiCnt = getLongData(eElement, "audiCnt");
                        long audiInten = getLongData(eElement, "audiInten");
                        double audiChange = getDoubleData(eElement, "audiChange");
                        long audiAcc = getLongData(eElement, "audiAcc");
                        long scrnCnt = getLongData(eElement, "scrnCnt");
                        long showCnt = getLongData(eElement, "showCnt");
                        BoxOffice boxOffice = new BoxOffice(
                                rnum, rank, rankInten, rankOldAndNew,
                                movieCd, movieNm, openDt,
                                salesAmt, salesShare, salesInten,
                                salesChange, salesAcc, audiCnt,
                                audiInten, audiChange, audiAcc,
                                scrnCnt, showCnt
                        );

                        list.add(boxOffice);

                    } catch (Exception e) {
                        System.out.println("데이터가 잘못되었습니다!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String getStrData(Element eElement, String tagName) {
        try {
            return eElement.getElementsByTagName(tagName).item(0).getTextContent();
        } catch (Exception e) {
            return "-";
        }
    }

    private static int getIntData(Element eElement, String tagName) {
        try {
            return Integer.parseInt(eElement.getElementsByTagName(tagName).item(0).getTextContent());
        } catch (Exception e) {
            return 0;
        }
    }

    private static long getLongData(Element eElement, String tagName) {
        try {
            return Long.parseLong(eElement.getElementsByTagName(tagName).item(0).getTextContent());
        } catch (Exception e) {
            return 0;
        }
    }

    private static double getDoubleData(Element eElement, String tagName) {
        try {
            return Double.parseDouble(eElement.getElementsByTagName(tagName).item(0).getTextContent());
        } catch (Exception e) {
            return 0.0;
        }
    }


}
