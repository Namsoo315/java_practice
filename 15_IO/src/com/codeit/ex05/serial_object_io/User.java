package com.codeit.ex05.serial_object_io;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
// 객체 직렬화 Interface 사용법
// 객체 -> 직렬화 -> 파일(데이터) (통신으로 보낼 수 있는) -> 역직렬화 -> 객체
public class User implements Serializable {

	// @Serial version, 버전이 달라지면 직렬화가 되지 않음!!, 없어도 문제는 없다.
	@Serial
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int age;
	private String email;

	public User() {
	}

	public User(String id, String name, int age, String email) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
			"id='" + id + '\'' +
			", name='" + name + '\'' +
			", age=" + age +
			", email='" + email + '\'' +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return age == user.age && Objects.equals(id, user.id) && Objects.equals(name, user.name)
			&& Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, age, email);
	}
}
