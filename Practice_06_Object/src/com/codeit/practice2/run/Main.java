package com.codeit.practice2.run;

import com.codeit.practice2.model.Book;

public class Main {
	public static void main(String[] args) {
		Book b1 = new Book();
		Book b2 = new Book("매개변수 3개 책", "생성자3", "남현수");
		Book b3 = new Book("매개변수 5개 책", "생성자5", "남현수", 15000, 10.0);

		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);

	}
}
