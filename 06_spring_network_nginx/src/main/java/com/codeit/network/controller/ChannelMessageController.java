package com.codeit.network.controller;

import com.codeit.network.dto.message.MessageCreateRequest;
import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.service.ChannelMessageService;
import com.codeit.network.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelMessageController {

    private final ChannelMessageService channelMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    // STOMP: 클라에서 /pub/channels/{channelId}/send 로 전송
    @MessageMapping("/channels/{channelId}/send")
    public void sendChannelMessageStomp(
            @DestinationVariable Long channelId,
            MessageCreateRequest request,
            Principal principal
    ) {
        if (principal == null) {
            throw new IllegalStateException("로그인 정보가 없습니다.");
        }

        Authentication authentication = (Authentication) principal;
        Object principalObj = authentication.getPrincipal();

        if (!(principalObj instanceof MyUserDetails myUserDetails)) {
            throw new IllegalStateException("인증 정보가 올바르지 않습니다.");
        }

        Long userId = myUserDetails.getUserDto().id();

        MessageResponse saved =
                channelMessageService.sendChannelMessageFromStomp(channelId, request, userId);

        // 구독 경로: /sub/channels/{channelId}
        messagingTemplate.convertAndSend("/sub/channels/" + channelId, saved);
    }

    // HTTP: 채널 입장 시 전체 메시지
    @GetMapping("/{channelId}/messages")
    @ResponseBody
    public List<MessageResponse> getChannelMessages(@PathVariable Long channelId) {
        return channelMessageService.getChannelMessages(channelId);
    }
}
