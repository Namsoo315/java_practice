package com.codeit.network.controller;


import com.codeit.network.dto.channel.ChannelCreateRequest;
import com.codeit.network.dto.channel.ChannelResponse;
import com.codeit.network.dto.channel.MyChannelResponse;
import com.codeit.network.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    // 채널 생성
    @PostMapping
    public ChannelResponse create(@RequestBody ChannelCreateRequest request) {
        return channelService.create(request);
    }

    // 채널 삭제
    @DeleteMapping("/{channelId}")
    public void delete(@PathVariable Long channelId) {
        channelService.delete(channelId);
    }

    // 채널 가입
    @PostMapping("/{channelId}/join")
    public ChannelResponse join(@PathVariable Long channelId) {
        return channelService.join(channelId);
    }

    // 전체 채널 목록
    @GetMapping
    public List<ChannelResponse> getAllChannels() {
        return channelService.getAllChannels();
    }

    // 내가 가입한 채널 목록
    @GetMapping("/my")
    public List<MyChannelResponse> getMyChannels() {
        return channelService.getMyChannels();
    }
}

