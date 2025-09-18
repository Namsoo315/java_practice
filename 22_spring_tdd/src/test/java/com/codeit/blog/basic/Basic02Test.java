package com.codeit.blog.basic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Basic02Test {

  // 1) 기본 검증 메서드
  @Test
  void testBasicAssertions() {

    assert 1 == 1;  // assert 키워드로 검증 가능!!
    assertNull(null);

  }

  @Test
  void testAssertions() {
    String name = "codeit";
    int age = 23;

    assertAll(
        () -> assertEquals(name, "codeit"),
        () -> assertEquals(age, 23),
        () -> assertTrue(name.contains("co"))
    );
  }

  @Test
  void testExceptionAssertions() {
    assertThrows(ArithmeticException.class, () -> divide(10, 0));
    assertDoesNotThrow(() -> divide(10, 0));
  }

  public int divide(int a, int b) {
    return a / b;
  }

}
