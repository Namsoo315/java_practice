package com.codeit.map.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.codeit.map.model.Member;

public class MemberController {
	private final Map<String, Member> map = new HashMap<>();
	
	public MemberController() {
		map.put("test1", new Member("1234", "홍길동"));
		map.put("test2", new Member("1234", "홍길동"));
		map.put("test3", new Member("1234", "홍길동"));
		map.put("test4", new Member("1234", "홍길동"));
		map.put("test5", new Member("1234", "홍길동"));
	}

	public boolean joinMembership(String id, Member m) {
		// 여기 채우세요.
	}

	public String login(String id, String password) {
		// 여기 채우세요.
	}

	public boolean changePassword(String id, String oldPw, String newPw) {
		// 여기 채우세요.
	}

	public boolean changeName(String id, String newName) {
		// 여기 채우세요.
	}

	public TreeMap<String, Member> sameName(String name) {
		// 여기 채우세요.

	}
}















