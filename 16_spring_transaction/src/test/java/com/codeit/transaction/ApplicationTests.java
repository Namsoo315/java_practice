package com.codeit.transaction;

import com.codeit.transaction.repository.DeliveryRepository;
import com.codeit.transaction.repository.OrdersRepository;
import com.codeit.transaction.service.OrderDeliveryService;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ApplicationTests {

    @Autowired
    OrderDeliveryService orderDeliveryService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    DeliveryRepository deliveryRepository;


    private static final Long userId = 1L;
    private static final Long productId = 1L;

    @Test
    @DisplayName("정상 시나리오")
    void test1() throws Exception {
        long beforeOrders = ordersRepository.count();
        long beforeDeliveries = deliveryRepository.count();

        orderDeliveryService.placeOrder(userId, productId, 2, "서울시 강남구 역삼동");

        // 커밋 확인
        long afterOrders = ordersRepository.count();
        long afterDelivery = deliveryRepository.count();
        assertEquals(beforeOrders + 1, afterOrders);
        assertEquals(beforeDeliveries + 1, afterDelivery);

        System.out.println("@@@ beforeOrders: " + beforeOrders);
        System.out.println("@@@ beforeDeliveries : " + beforeDeliveries);
        System.out.println("@@@ afterOrders: " + afterOrders);
        System.out.println("@@@ afterDelivery : " + afterDelivery);
    }

    @Test
    @DisplayName("런타임 에러")
    void test2() throws Exception {
        long beforeOrders = ordersRepository.count();
        long beforeDeliveries = deliveryRepository.count();

        orderDeliveryService.placeOrderV2(userId, productId, 2, "서울시 강남구 역삼동");

        // 커밋 확인
        long afterOrders = ordersRepository.count();
        long afterDelivery = deliveryRepository.count();
        assertEquals(beforeOrders, afterOrders);
        assertEquals(beforeDeliveries, afterDelivery);

        System.out.println("@@@ beforeOrders: " + beforeOrders);
        System.out.println("@@@ beforeDeliveries : " + beforeDeliveries);
        System.out.println("@@@ afterOrders: " + afterOrders);
        System.out.println("@@@ afterDelivery : " + afterDelivery);
    }


    @Test
    @DisplayName("Checked 에러")
    void test3() throws IOException {
        long beforeOrders = ordersRepository.count();
        long beforeDeliveries = deliveryRepository.count();

        orderDeliveryService.placeOrderV3(userId, productId, 2, "서울시 강남구 역삼동");

        // 커밋 확인
        long afterOrders = ordersRepository.count();
        long afterDelivery = deliveryRepository.count();
        assertEquals(beforeOrders + 1, afterOrders);
        assertEquals(beforeDeliveries + 1, afterDelivery);

        System.out.println("@@@ beforeOrders: " + beforeOrders);
        System.out.println("@@@ beforeDeliveries : " + beforeDeliveries);
        System.out.println("@@@ afterOrders: " + afterOrders);
        System.out.println("@@@ afterDelivery : " + afterDelivery);
    }

    @Test
    @DisplayName("내부 메서드 호출 시나리오")
    void test4() {
        long beforeOrders = ordersRepository.count();
        long beforeDeliveries = deliveryRepository.count();

        assertThrows(Exception.class, () ->
            orderDeliveryService.placeOrderTest4(userId, productId, 2, "서울시 강남구 역삼동")
        );

        // 롤백 확인
        long afterOrders = ordersRepository.count();
        long afterDelivery = deliveryRepository.count();
        assertEquals(beforeOrders, afterOrders);
        assertEquals(beforeDeliveries , afterDelivery);

        System.out.println("@@@ beforeOrders: " + beforeOrders);
        System.out.println("@@@ beforeDeliveries : " + beforeDeliveries);
        System.out.println("@@@ afterOrders: " + afterOrders);
        System.out.println("@@@ afterDelivery : " + afterDelivery);
    }

    @Test
    @DisplayName("this로 거는 것")
    void test5() {
        long beforeOrders = ordersRepository.count();
        long beforeDeliveries = deliveryRepository.count();

        assertThrows(Exception.class, () ->
            orderDeliveryService.placeOrderTest5(userId, productId, 2, "서울시 강남구 역삼동")
        );

        // 롤백 확인
        long afterOrders = ordersRepository.count();
        long afterDelivery = deliveryRepository.count();
        System.out.println("@@@ beforeOrders: " + beforeOrders);
        System.out.println("@@@ beforeDeliveries : " + beforeDeliveries);
        System.out.println("@@@ afterOrders: " + afterOrders);
        System.out.println("@@@ afterDelivery : " + afterDelivery);

        assertEquals(beforeOrders, afterOrders);
        assertEquals(beforeDeliveries, afterDelivery);
    }
}
