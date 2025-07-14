package com.codeit.library.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.codeit.library.model.Book;

public class BookController {
	private List<Book> bookList = new ArrayList<>();

	public BookController() {
		bookList.add(new Book("자바의 정석", "남궁 성", "코딩", 20000));
		bookList.add(new Book("자바의 정석-기본편", "남궁 성", "코딩", 30000));
		bookList.add(new Book("쉽게 배우는 알고리즘", "문병로", "코딩", 15000));
		bookList.add(new Book("자바로 배우는 알고리즘", "문병로", "코딩", 15000));
		bookList.add(new Book("데일 카네기 인간관계론", "데일 카네기", "인문", 12500));
		bookList.add(new Book("백년운동", "정성근", "의료", 16500));
		bookList.add(new Book("스즈메의 문단속", "신카이 마코토", "기타", 11700));
	}

	public void insertBook(Book book) {
		bookList.add(book);
	}

	public List<Book> selectList() {
		return bookList;
	}

	// 키워드로 책을 검색
	public List<Book> searchBook(String keyword) {
		List<Book> list = new ArrayList<>();
		for (Book book : bookList) {
			if (book.getTitle() != null && book.getTitle().contains(keyword)) {
				list.add(book);
			}
		}

		return list;
	}

	public Book deleteBook(String title, String author) {
		bookList.removeIf(book -> book.getTitle().equals(title) && book.getAuthor().equals(author));
		return bookList.isEmpty() ? null : bookList.get(0);
	}

	public int ascBook() {
		Collections.sort(bookList);

		return 1;
	}
}






