package com.codeit.ex05.serial_object_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectIO {

	// 객체 단위로 IO 하는 메서드
	// 저장
	public static boolean saveObject(String path, Object obj) {
		try (FileOutputStream fos = new FileOutputStream(path);
			 ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(obj);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 불러오기
	public static Object loadObject(String path) {
		try (FileInputStream fis = new FileInputStream(path);
			 ObjectInputStream ois = new ObjectInputStream(fis);) {

			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void userObject() {
		User user1 = new User("test1", "홍길동", 31, "test@email.com");
		boolean result = saveObject("15_IO/" + user1.getId(), user1);        // 직렬화
		System.out.println(result);

		// 읽어오기
		User user2 = (User)loadObject("15_IO/" + user1.getId());        //역질렬화
		System.out.println("user1 = " + user1);
		System.out.println("user2 = " + user2);

	}

	public static void main(String[] args) {
		userObject();
	}
}
