package com.codeit.blog.basic;

import org.junit.jupiter.api.Test;

public class Basic03Test {

  private int counter = 0;

  @Test   //테스트 어노테이션의 특징은 각각을 독립적인 환경에서 객체를 생성함으로 공유가 안됨.
  void test1() {
    counter++;
    System.out.println("counter = " + counter);
  }

  @Test
  void test2() {
    counter++;
    System.out.println("counter = " + counter);
  }

}
