package com.codeit.practice2.model;

public class Book {
	private String title;
	private String publisher;
	private String author;
	private int price;
	private double discountRate;

	public Book() {
		this.title = "기본생성자";
		this.publisher = "생성자1";
		this.author = "남현수";
		this.price = 10000;
		this.discountRate = 5.0;
	}

	public Book(String title, String publisher, String author) {
		this.title = title;
		this.publisher = publisher;
		this.author = author;
	}

	public Book(String title, String publisher, String author, int price, double discountRate) {
		this.title = title;
		this.publisher = publisher;
		this.author = author;
		this.price = price;
		this.discountRate = discountRate;
	}

	@Override
	public String toString() {
		return "Book{" +
			"title='" + title + '\'' +
			", publisher='" + publisher + '\'' +
			", author='" + author + '\'' +
			", price=" + price +
			", discountRate=" + discountRate +
			'}';
	}
}
