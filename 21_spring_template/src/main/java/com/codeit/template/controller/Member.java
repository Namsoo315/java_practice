package com.codeit.template.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
}
