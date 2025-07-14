package com.codeit.library.view;

import java.util.List;
import java.util.Scanner;

import com.codeit.library.controller.BookController;
import com.codeit.library.model.Book;

public class MenuView {
	private final Scanner sc = new Scanner(System.in);
	private final BookController bc = new BookController();

	public void mainMenu() {
		while (true) {
			System.out.println("== Welcome Codeit Library ==");
			System.out.println("1. 새 도서 추가");
			System.out.println("2. 도서 전체 조회");
			System.out.println("3. 도서 검색 조회");
			System.out.println("4. 도서 삭제");
			System.out.println("5. 도서 명 오름차순 정렬");
			System.out.println("9. 프로그램 끝내기");
			System.out.print("메뉴 번호 : ");

			String str = sc.nextLine();
			int selection = Integer.parseInt(str);

			switch (selection) {
			case 1:
				insertBook();
				break;
			case 2:
				selectList();
				break;
			case 3:
				searchBook();
				break;
			case 4:
				deleteBook();
				break;
			case 5:
				ascBook();
				break;
			case 9:
				System.out.println("프로그램 종료");
				return;
			default:
				System.out.println("잘못 입력하였습니다. 다시 입력해주세요");
				continue;
			}
			System.out.println();
		}
	}

	String[] categoryArr = {"","인문","코딩","의료","기타"};
	public void insertBook() {
		try {
			System.out.println("===== 새 도서 추가 =====");
			System.out.println("책 정보를 입력해주세요.");
			System.out.print("도서 명 : ");
			String title = sc.nextLine();
			System.out.print("저자 명 : ");
			String author = sc.nextLine();
			System.out.print("장르(1. 인문 / 2. 코딩 / 3. 의료 / 4. 기타) : ");
			String category = sc.nextLine();
			int categoryNum = Integer.parseInt(category);
			category = categoryArr[categoryNum];
			System.out.print("가격 :  ");
			String priceStr = sc.nextLine();
			
			int price = Integer.parseInt(priceStr);
			Book book = new Book(title, author, category, price);
			System.out.println("입력 정보 : " + book);
			bc.insertBook(book);
		} catch (Exception e) {
			System.out.println("잘못된 입력입니다.");
		}
	}

	public void selectList() {
		List<Book> list = bc.selectList(); 
		System.out.println("===== 도서 전체 조회 =====");
		System.out.println("총 갯수 : " + list.size());
		int count = 1;
		for(Book b : list) {
			System.out.println(count++ +") "+b.getTitle() + "/" + 
								b.getAuthor() + "/" +b.getCategory()+ "/"+ b.getPrice());
		}
		System.out.println();
	}

	public void searchBook() {
		System.out.println("===== 도서 검색 =====");
		System.out.print("검색 키워드 : ");
		String keyword = sc.nextLine();
		System.out.println("===== 검색 결과 =====");
		List<Book> list = bc.searchBook(keyword);
		System.out.println("총 갯수 : " + list.size());
		int count = 1;
		for(Book b : list) {
			System.out.println(count++ +") "+b.getTitle() + "/" + 
								b.getAuthor() + "/" +b.getCategory()+ "/"+ b.getPrice());
		}
		System.out.println();
	}

	public void deleteBook() {
		System.out.print("도서 명 : ");
		String title = sc.nextLine();
		System.out.print("저자 명 : ");
		String author = sc.nextLine();
		Book b = bc.deleteBook(title, author);
		if(b != null) {
			System.out.println("삭제 되었습니다.");
		} else {
			System.out.println("삭제 할 도서를 찾을 수 없습니다.");
		}
	}

	public void ascBook() {
		bc.ascBook();
	}
}
