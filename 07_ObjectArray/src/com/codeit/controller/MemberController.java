package com.codeit.controller;

import java.util.Arrays;

import com.codeit.model.Member;

public class MemberController {
	public static int MAX_SIZE = 10;    // 배열의 최대 크기
	private final Member[] mArray = new Member[MAX_SIZE]; // 배열

	public int existMemberNum() {
		int count = 0;

		for (Member member : mArray) {
			if (member != null) {
				count++;
			}
		}
		return count;
	}

	// private int searchIdForIndex(String id) {
	//
	// }

	public boolean checkId(String inputId) {
		for (Member member : mArray) {
			if (member != null && member.getId() != null && member.getId().equals(inputId)) {
				return true;
			}
		}
		return false;
	}

	public Member searchId(String id) {
		if (id == null)
			return null;

		for (Member value : mArray) {
			if (value != null && value.getId().equals(id)) {
				return value;
			}
		}

		return null;
	}

	public Member[] searchName(String name) {
		if (name == null)
			return null;

		for (Member value : mArray) {
			if (value != null && value.getName().equals(name)) {
				return new Member[] {value};
			}
		}

		return null;
	}

	public Member[] searchEmail(String email) {
		if (email == null)
			return null;

		for (Member value : mArray) {
			if (value != null && value.getEmail().equals(email)) {
				return new Member[] {value};
			}
		}

		return null;
	}

	public boolean insertMember(Member member) {
		if (mArray[existMemberNum()] == null) {
			mArray[existMemberNum()] = member;
			return true;
		} else {
			return false;
		}
	}

	public boolean updatePassword(String id, String password) {
		for (Member value : mArray) {
			if (value.getId().equals(id)) {
				value.setPassword(password);
				return true;
			}
		}
		return false;
	}

	public boolean updateName(String id, String name) {
		for (Member value : mArray) {
			if (value.getId().equals(id)) {
				value.setName(name);
				return true;
			}
		}
		return false;
	}

	public boolean updateEmail(String id, String email) {
		for (Member value : mArray) {
			if (value.getId().equals(id)) {
				value.setEmail(email);
				return true;
			}
		}
		return false;
	}

	public boolean delete(String id) {
		if(id == null) {
			return false;
		}
		for(int i= 0; i<mArray.length; i++) {
			if(mArray[i] != null && id.equals(mArray[i].getId())){
				mArray[i] = null;
				return true;
			}
		}
		return false;
	}

	public void delete() {
		Arrays.fill(mArray, null);
	}

	public Member[] printAll() {
		return mArray.clone();
	}

}





