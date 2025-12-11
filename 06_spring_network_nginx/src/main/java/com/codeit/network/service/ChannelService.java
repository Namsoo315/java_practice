package com.codeit.network.service;

import com.codeit.network.dto.channel.ChannelCreateRequest;
import com.codeit.network.dto.channel.ChannelResponse;
import com.codeit.network.dto.channel.MyChannelResponse;
import com.codeit.network.dto.user.UserDto;
import com.codeit.network.entity.*;
import com.codeit.network.mapper.ChannelMapper;
import com.codeit.network.repository.ChannelMemberRepository;
import com.codeit.network.repository.ChannelRepository;
import com.codeit.network.repository.UserRepository;
import com.codeit.network.security.MyUserDetails;
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
    private final UserRepository userRepository;

    @Transactional
    public ChannelResponse create(ChannelCreateRequest request) {
        UserDto loginUser = getCurrentUser();
        User userEntity = getCurrentUserEntity(loginUser);

        Channel channel = Channel.builder()
                .name(request.name())
                .description(request.description())
                .privateChannel(request.privateChannel())
                .build();

        Channel savedChannel = channelRepository.save(channel);

        ChannelMember owner = ChannelMember.builder()
                .channel(savedChannel)
                .user(userEntity)
                .memberRole(ChannelMemberRole.OWNER)
                .build();

        channelMemberRepository.save(owner);

        return channelMapper.toResponse(savedChannel);
    }


    @Transactional
    public void delete(Long channelId) {
        UserDto loginUser = getCurrentUser();
        User userEntity = getCurrentUserEntity(loginUser);

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 채널을 찾을 수 없습니다."));

        boolean isAdmin = loginUser.role() == Role.ADMIN;

        boolean isOwner = channelMemberRepository
                .findByChannelIdAndUserId(channelId, userEntity.getId())
                .map(cm -> cm.getMemberRole() == ChannelMemberRole.OWNER)
                .orElse(false);

        if (!isAdmin && !isOwner) {
            throw new IllegalStateException("채널을 삭제할 권한이 없습니다.");
        }

        channelRepository.delete(channel);
    }

    @Transactional
    public ChannelResponse join(Long channelId) {
        UserDto loginUser = getCurrentUser();
        User userEntity = getCurrentUserEntity(loginUser);

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 채널을 찾을 수 없습니다."));

        boolean alreadyJoined =
                channelMemberRepository.existsByChannelIdAndUserId(channelId, userEntity.getId());

        if (alreadyJoined) {
            throw new IllegalStateException("이미 해당 채널에 가입되어 있습니다.");
        }

        ChannelMember member = ChannelMember.builder()
                .channel(channel)
                .user(userEntity)
                .memberRole(ChannelMemberRole.MEMBER)
                .build();

        channelMemberRepository.save(member);

        return channelMapper.toResponse(channel);
    }


    @Transactional(readOnly = true)
    public List<ChannelResponse> getAllChannels() {
        return channelRepository.findAll().stream()
                .map(channelMapper::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<MyChannelResponse> getMyChannels() {
        UserDto loginUser = getCurrentUser();

        return channelMemberRepository.findByUserId(loginUser.id())
                .stream()
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


    private UserDto getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof MyUserDetails myUserDetails) {
            return myUserDetails.getUserDto();
        }
        throw new IllegalStateException("알 수 없는 인증 정보입니다.");
    }


    private User getCurrentUserEntity(UserDto dto) {
        return userRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalStateException("DB에서 사용자를 찾을 수 없습니다."));
    }
}
