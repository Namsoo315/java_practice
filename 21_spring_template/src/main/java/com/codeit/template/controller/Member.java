package com.codeit.template.controller;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class Member{
	private String id;
	private String name;
	private int age;
	private String gender;
	private String address; 
	private List<String> devLang;
	private String nullValue;

	public Member(String id, String name, int age, String gender, String address, List<String> devLang,
		String nullValue) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.devLang = devLang;
		this.nullValue = nullValue;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getAddress() {
		return address;
	}

	public List<String> getDevLang() {
		return devLang;
	}

	public String getNullValue() {
		return nullValue;
	}
}
