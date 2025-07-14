package com.codeit.map.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.codeit.map.model.Member;

public class MemberController {
	private final Map<String, Member> map = new HashMap<>();

	public MemberController() {

	}

	public boolean joinMembership(String id, Member m) {
		if (map.containsKey(id)) {
			return false;
		}

		map.put(id, m);
		return true;
	}

	public String login(String id, String password) {
		if (map.containsKey(id)) {
			if (password.equals(map.get(id).getPassword())) {
				return map.get(id).getName();
			}
		}
		return null;
	}

	public boolean changePassword(String id, String oldPw, String newPw) {
		if (map.containsKey(id)) {
			if (map.get(id).getPassword().equals(oldPw)) {
				map.get(id).setPassword(newPw);
				return true;
			}
		}
		return false;
	}

	public void changeName(String id, String newName) {
		map.get(id).setName(newName);
	}

	public TreeMap<String, Member> sameName(String name) {
		TreeMap<String, Member> result = new TreeMap<>();
		for (String key : map.keySet()) {
			Member m = map.get(key);
			if (m.getName().equals(name)) {
				result.put(key, m);
			}
		}

		return result;
	}
}















