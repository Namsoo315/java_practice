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

    // 채널 채팅 - Polling 전용 페이지
    @GetMapping("/chat/channel/polling")
    public String channelPollingChatPage() {
        return "channel-chat-polling";   // templates/channel-chat-polling.html
    }

    // 채널 채팅 - Long Polling 전용 페이지
    @GetMapping("/chat/channel/long-polling")
    public String channelLongPollingChatPage() {
        return "channel-chat-longpolling"; // templates/channel-chat-longpolling.html
    }

    // DM 채팅 - Polling 전용 페이지
    @GetMapping("/chat/dm/polling")
    public String dmPollingChatPage() {
        return "dm-chat-polling";        // templates/dm-chat-polling.html
    }

    // DM 채팅 - Long Polling 전용 페이지
    @GetMapping("/chat/dm/long-polling")
    public String dmLongPollingChatPage() {
        return "dm-chat-longpolling";    // templates/dm-chat-longpolling.html
    }
}
