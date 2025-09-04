package com.codeit.transaction.service;

import com.codeit.transaction.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MailService {
    public void sendOrderConfirmation(Orders order, boolean isSuccess) throws IOException {
        System.out.println("[MAIL] 주문 확인 메일 발송 완료 orderId=" + order.getId());
        if(!isSuccess){
            log.info("예외발생!!");
            throw new IOException("예외 발생!");
        }
    }
}