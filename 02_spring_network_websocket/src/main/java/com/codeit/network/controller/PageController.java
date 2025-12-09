package com.codeit.network.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 메인 인덱스 페이지
    @GetMapping("/")
    public String index() {
        return "index";          // templates/index.html
    }

    // 로그인 / me 테스트 페이지
    @GetMapping("/login-page")
    public String loginPage() {
        return "login";          // templates/login.html
    }

    // 채널 생성 / 삭제 / 가입 / 조회 페이지
    @GetMapping("/channels-page")
    public String channelsPage() {
        return "channels";       // templates/channels.html
    }

    // 채널 채팅 - WebSocket(STOMP)
    @GetMapping("/chat/channel")
    public String channelChatPage() {
        return "channel-chat";   // templates/channel-chat.html
    }

    // DM 채팅 - WebSocket(STOMP)
    @GetMapping("/chat/dm")
    public String dmChatPage() {
        return "dm-chat";        // templates/dm-chat.html
    }
}
