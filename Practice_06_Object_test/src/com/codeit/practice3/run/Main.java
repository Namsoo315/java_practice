package com.codeit.practice3.run;

import com.codeit.practice3.model.Employee;

public class Main {
	public static void main(String[] args) {
		Employee e1 = new Employee();
		e1.setEmpNo(100);
		e1.setEmpName("홍길동");
		e1.setDept("영업부");
		e1.setJob("과장");
		e1.setAge(25);
		e1.setGender('남');
		e1.setSalary(2500000);
		e1.setBonusPoint(0.05);
		e1.setPhone("010-1234-5678");
		e1.setAddress("서울시 강남구");

		System.out.println("===============================");
		System.out.println("e1.getEmpNo() = " + e1.getEmpNo());
		System.out.println("e1.getEmpName() = " + e1.getEmpName());
		System.out.println("e1.getDept() = " + e1.getDept());
		System.out.println("e1.getJob() = " + e1.getJob());
		System.out.println("e1.getAge() = " + e1.getAge());
		System.out.println("e1.getGender() = " + e1.getGender());
		System.out.println("e1.getSalary() = " + e1.getSalary());
		System.out.println("e1.getBonusPoint() = " + e1.getBonusPoint());
		System.out.println("e1.getPhone() = " + e1.getPhone());
		System.out.println("e1.getAddress() = " + e1.getAddress());
		System.out.println("===============================");

		System.out.println(e1);
	}
}
