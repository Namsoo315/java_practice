package com.codeit.map.model;

public class Member {
	private String id;
	private String password;
	private String name;

	public Member(){

	}

	public Member(String password, String name) {
		this.password = password;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Member{");
		sb.append("id='").append(id).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
