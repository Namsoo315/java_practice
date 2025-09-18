package com.codeit.blog.basic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class Basic01Test {

  @BeforeAll  // 전체 테스트 케이스 실행 전 한번만
  public static void initAll() {
    System.out.println("Before All - 전체 테스트 케이스 실행 전 한번만");

  }

  @BeforeEach
  public void init() {
    System.out.println("Before Each - 각 테스트 시작 전에 실행");
  }

  @Test
  @DisplayName("덧셈 테스트")
  void add_Test() {    //테스트 메서드는 언더바 규칙을 사용해도 됨.
    int result = 2 + 3;
    assert result == 6;   // 우항이 6이면 성공
    System.out.println("addTest");
    Assertions.assertEquals(6, result);
  }

  @Test
  @DisplayName("나눗셈 테스트")
  void divisionTest() {
    int result = 10 / 2;
    System.out.println("divisionTest");
    assert result == 10;
    Assertions.assertEquals(10, result);
  }
}
