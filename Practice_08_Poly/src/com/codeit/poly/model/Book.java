package com.codeit.poly.model;

public class Book {
	private String title;
	private String author;
	private String publisher;

	public Book() {
	}

	public Book(String title, String author, String publisher) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Book{");
		sb.append("title='").append(title).append('\'');
		sb.append(", author='").append(author).append('\'');
		sb.append(", publisher='").append(publisher).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
