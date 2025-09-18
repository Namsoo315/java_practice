package com.codeit.blog.basic;

import org.junit.jupiter.api.*;

public class Basic03Test {

    private int counter = 0;

    @Test
        //테스트 어노테이션의 특징은 각각을 독립적인 환경에서 객체를 생성함으로 공유가 안됨.
    void test1() {
        counter++;
        System.out.println("counter = " + counter);
    }

    @Test
    void test2() {
        counter++;
        System.out.println("counter = " + counter);
    }

    @Test
    void test3() {
        counter++;
        System.out.println("counter = " + counter);
    }

    // 2) 실행 순서 지정 예시
    @Test
    @Order(2)
    void secondTest() {
        System.out.println("Second");
    }

    @Test
    @Order(1)
    void firstTest() {
        System.out.println("First");
    }

    @Test
    @Order(3)
    void thirdTest() {
        System.out.println("Third");
    }

    //  3) 중첩 테스트 + 태깅
    @Nested
    class MathTests {
        @Test
        void addTest() {
            Assertions.assertEquals(5, 3 + 2);
        }

        @Test
        void divisionTest() {
            Assertions.assertEquals(5, 10 / 2);
        }
    }

    @Test
    @Tag("slow")
    void slow() throws InterruptedException {
        Thread.sleep(1000);
        Assertions.assertTrue(true);
    }

}
