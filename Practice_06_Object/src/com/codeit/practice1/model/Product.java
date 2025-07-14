package com.codeit.practice1.model;

public class Product {
	private String pName;
	private int price;
	private String brand;

	public Product() {
		this.pName = "라면";
		this.price = 10000;
		this.brand = "삼양";
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Product{");
		sb.append("pName=").append(pName).append("\'");
		sb.append("price=").append(price).append("원");
		sb.append("brand=").append(brand).append("\'");
		sb.append("}");

		return sb.toString();
	}
}
