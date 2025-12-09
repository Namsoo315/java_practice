package com.codeit.network.controller;

import com.codeit.network.dto.message.MessageCreateRequest;
import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.entity.User;
import com.codeit.network.service.DirectMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/dm") // 초기 DM 로딩용 HTTP API
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService directMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    // 0) DM 전송 (STOMP)
    // 클라이언트 → /app/dm/{receiverUserId}/send
    // 서버 → /topic/dm.{receiverUserId}, /topic/dm.{senderUserId}
    @MessageMapping("/dm/{receiverUserId}/send")
    public void sendDirectMessageStomp(
            @DestinationVariable Long receiverUserId,
            MessageCreateRequest request,
            Principal principal
    ) {
        if (principal == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        User sender = (User) ((Authentication) principal).getPrincipal();
        Long senderId = sender.getId();

        log.info("[STOMP-DM] to={}, fromUserId={}, request={}", receiverUserId, senderId, request);

        // DB 저장
        MessageResponse saved =
                directMessageService.sendDirectMessageFromStomp(receiverUserId, request, senderId);

        // 수신자에게 전송
        messagingTemplate.convertAndSend("/topic/dm." + receiverUserId, saved);

        // 보낸 사람에게도 echo (상대에게 보낸 메시지를 내 쪽지도에서 바로 보이게)
        if (!receiverUserId.equals(senderId)) {
            messagingTemplate.convertAndSend("/topic/dm." + senderId, saved);
        }
    }

    // 1) DM 초기 로딩 (HTTP)
    @GetMapping("/messages")
    @ResponseBody
    public List<MessageResponse> getInbox() {
        log.info("[GET] /api/dm/messages 초기 로딩 요청");
        return directMessageService.getInbox();
    }
}
