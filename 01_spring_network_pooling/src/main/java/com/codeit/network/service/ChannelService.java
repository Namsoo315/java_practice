package com.codeit.network.service;

import com.codeit.network.dto.channel.ChannelCreateRequest;
import com.codeit.network.dto.channel.ChannelResponse;
import com.codeit.network.dto.channel.MyChannelResponse;
import com.codeit.network.entity.*;
import com.codeit.network.mapper.ChannelMapper;
import com.codeit.network.repository.ChannelMemberRepository;
import com.codeit.network.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final ChannelMapper channelMapper;

    // 채널 생성 + 생성자를 OWNER로 가입
    @Transactional
    public ChannelResponse create(ChannelCreateRequest request) {
        User loginUser = getCurrentUser();

        Channel channel = Channel.builder()
                .name(request.name())
                .description(request.description())
                .privateChannel(request.privateChannel())
                .build();

        Channel savedChannel = channelRepository.save(channel);

        ChannelMember owner = ChannelMember.builder()
                .channel(savedChannel)
                .user(loginUser)
                .memberRole(ChannelMemberRole.OWNER)
                .build();

        channelMemberRepository.save(owner);

        return channelMapper.toResponse(savedChannel);
    }

    // 채널 삭제 (ADMIN 또는 OWNER만 가능)
    @Transactional
    public void delete(Long channelId) {
        User loginUser = getCurrentUser();

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 채널을 찾을 수 없습니다."));

        boolean isAdmin = loginUser.getRole() == UserRole.ADMIN;

        boolean isOwner = channelMemberRepository.findByChannelIdAndUserId(channelId, loginUser.getId())
                .map(cm -> cm.getMemberRole() == ChannelMemberRole.OWNER)
                .orElse(false);

        if (!isAdmin && !isOwner) {
            throw new IllegalStateException("채널을 삭제할 권한이 없습니다.");
        }

        // ON DELETE CASCADE에 의해 관련 messages, channel_members 자동 삭제
        channelRepository.delete(channel);
    }

    // 채널 가입 (MEMBER로)
    @Transactional
    public ChannelResponse join(Long channelId) {
        User loginUser = getCurrentUser();

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 채널을 찾을 수 없습니다."));

        boolean alreadyJoined = channelMemberRepository.existsByChannelIdAndUserId(channelId, loginUser.getId());
        if (alreadyJoined) {
            throw new IllegalStateException("이미 해당 채널에 가입되어 있습니다.");
        }

        ChannelMember member = ChannelMember.builder()
                .channel(channel)
                .user(loginUser)
                .memberRole(ChannelMemberRole.MEMBER)
                .build();

        channelMemberRepository.save(member);

        return channelMapper.toResponse(channel);
    }

    // 전체 채널 리스트 조회
    @Transactional(readOnly = true)
    public List<ChannelResponse> getAllChannels() {
        return channelRepository.findAll().stream()
                .map(channelMapper::toResponse)
                .toList();
    }

    // 내가 가입한 채널 리스트 조회
    @Transactional(readOnly = true)
    public List<MyChannelResponse> getMyChannels() {
        User loginUser = getCurrentUser();

        return channelMemberRepository.findByUserId(loginUser.getId()).stream()
                .map(cm -> new MyChannelResponse(
                        cm.getChannel().getId(),
                        cm.getChannel().getName(),
                        cm.getChannel().getDescription(),
                        cm.getChannel().isPrivateChannel(),
                        cm.getMemberRole(),
                        cm.getJoinedAt()
                ))
                .toList();
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User user)) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }
        return user;
    }
}
