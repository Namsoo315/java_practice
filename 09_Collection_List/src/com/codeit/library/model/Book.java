package com.codeit.library.model;

import java.util.Comparator;
import java.util.Objects;

public class Book implements Comparable<Book> {
	private String title;
	private String author;
	private String category;
	private int price;

	public Book() {
	}

	public Book(String title, String author, String category, int price) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Book{");
		sb.append("title='").append(title).append('\'');
		sb.append(", author='").append(author).append('\'');
		sb.append(", category='").append(category).append('\'');
		sb.append(", price=").append(price);
		sb.append('}');
		return sb.toString();
	}
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		Book book = (Book)o;
		return price == book.price && Objects.equals(title, book.title) && Objects.equals(author,
			book.author) && Objects.equals(category, book.category);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, author, category, price);
	}

	@Override
	public int compareTo(Book o) {
		return this.title.compareTo(o.title);
	}
}
