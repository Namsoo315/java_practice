package com.codeit.model;

public class Member {
	private String id;
	private String name;
	private String password;
	private String email;
	private char gender;
	private int age;

	public Member() {

	}

	public Member(String id, String name, String password, String email, char gender, int age) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Member{");
		sb.append("id='").append(id).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", gender=").append(gender);
		sb.append(", age=").append(age);
		sb.append('}');
		return sb.toString();
	}
}


