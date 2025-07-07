package com.codeit.ex03.branch;

public class BranchStatement {
	public static void main(String[] args) {

		int count = 0;
		for (int i = 1; i <= 1000; i++) {
			if (i % 2 == 0) {
				continue;
			}

			if (i % 31 == 0) {
				count++;
				if (count == 10){
					System.out.println(i);
					break;
				}
			}
		}

		System.out.println("count : " + count);
	}
}
