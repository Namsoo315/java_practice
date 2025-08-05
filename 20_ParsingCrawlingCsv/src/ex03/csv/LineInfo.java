package ex03.csv;

import java.util.List;

//메뉴ID	식당ID	메뉴명	메뉴가격	메뉴태그정보	등록일시
//1	858991	짜장면	6000	주재료 : 돼지고기,야채,면,밀가루 / 조리법 : 볶음 / 소스 : 춘장	2020-10-26 16:45

//메뉴ID	식당ID	메뉴명	메뉴가격	메뉴태그정보	등록일시
//2	858991	간짜장(2인이상)	7000	주재료 : 밀가루 / 조리법 : 짜장면 / 소스 : 짜장소스	2020-10-26 16:45

public class LineInfo {
	private List<String> header;
	private List<String> info;

	public LineInfo() {
		super();
	}

	public LineInfo(List<String> header, List<String> info) {
		super();
		this.header = header;
		this.info = info;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<String> getInfo() {
		return info;
	}

	public void setInfo(List<String> info) {
		this.info = info;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < header.size(); i++) {
			sb.append(header.get(i)).append(":").append(info.get(i)).append(", ");
		}
		return sb.toString();
	}
	
	public int getSize() {
		return header.size();
	}
	
	public String getData(String key) {
		int index = header.indexOf(key);
		if(index != -1) {
			return info.get(index);
		} else {
			return null;
		}
	}
}
