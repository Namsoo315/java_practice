package com.codeit.poly.controller;

import java.util.Arrays;

import com.codeit.poly.model.AniBook;
import com.codeit.poly.model.Book;
import com.codeit.poly.model.CookBook;
import com.codeit.poly.model.Member;

public class LibraryController {
	private Member member = null;
	private final Book[] bList = new Book[5];

	{
		bList[0] = new CookBook("백종원의 집밥", "백종원", "tvN", true);
		bList[1] = new AniBook("탑의신", "소고기", "내이놈", 16);
		bList[2] = new AniBook("루피의 원피스", "루피", "japan", 12);
		bList[3] = new CookBook("이혜정의 얼마나 맛있게요", "이혜정", "문학", false);
		bList[4] = new CookBook("최현석 날 따라해봐", "최현석", "소금책", true);
	}

	public void insertMember(Member member) {
		this.member = member;
	}

	public Member myInfo() {
		return member;
	}

	public Book[] selectAll() {
		return bList;
	}

	public Book[] searchBook(String keyword) {
		Book[] tempArray = new Book[bList.length];
		int count = 0;
		for(int i = 0; i < bList.length; i++) {
			if(bList[i].getTitle().contains(keyword)) {
				tempArray[count++] = bList[i];
			}
		}
		return Arrays.copyOf(tempArray, count);
	}

	public int rentBook(int index) {
		// 여기 작성하세요.

	}

}








