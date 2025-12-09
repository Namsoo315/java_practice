package com.codeit.network.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/channels-page")
    public String channelsPage() {
        return "channels";
    }

    @GetMapping("/chat/channel")
    public String channelChatPage() {
        return "channel-chat";   // 네가 사용중인 템플릿 이름에 맞춰서
    }

    @GetMapping("/chat/dm")
    public String dmChatPage() {
        return "dm-chat";        // 네가 사용중인 템플릿 이름에 맞춰서
    }

    // 🔥 SSE 모니터링 페이지
    @GetMapping("/sse-monitor")
    public String sseMonitorPage() {
        return "sse-monitor";    // templates/sse-monitor.html
    }


    @GetMapping("/sse-counter")
    public String sseCounterPage() {
        return "sse-counter";
    }
}
