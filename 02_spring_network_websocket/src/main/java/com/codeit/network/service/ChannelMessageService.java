package com.codeit.network.service;

import com.codeit.network.dto.message.MessageCreateRequest;
import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.entity.Channel;
import com.codeit.network.entity.Message;
import com.codeit.network.entity.MessageType;
import com.codeit.network.entity.User;
import com.codeit.network.mapper.MessageMapper;
import com.codeit.network.repository.ChannelMemberRepository;
import com.codeit.network.repository.ChannelRepository;
import com.codeit.network.repository.MessageRepository;
import com.codeit.network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelMessageService {

    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    // 1) HTTP: 채널 입장 시 전체 메시지 조회
    public List<MessageResponse> getChannelMessages(Long channelId) {
        var context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Object principal = context.getAuthentication().getPrincipal();
        if (!(principal instanceof User currentUser)) {
            throw new IllegalStateException("로그인 정보가 올바르지 않습니다.");
        }

        Long currentUserId = currentUser.getId();

        if (!channelMemberRepository.existsByChannelIdAndUserId(channelId, currentUserId)) {
            throw new IllegalArgumentException("채널에 가입되지 않았습니다.");
        }

        List<Message> messages = messageRepository
                .findByMessageTypeAndChannelIdOrderByIdAsc(MessageType.CHANNEL, channelId);

        return messages.stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    // 2) STOMP: 채널 메시지 전송 (userId는 컨트롤러에서 Principal로부터 전달)
    @Transactional
    public MessageResponse sendChannelMessageFromStomp(
            Long channelId,
            MessageCreateRequest request,
            Long userId
    ) {
        if (userId == null) {
            throw new IllegalStateException("WebSocket 인증 정보가 없습니다. 사용자 ID가 null 입니다.");
        }

        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다. id=" + userId));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 채널을 찾을 수 없습니다. id=" + channelId));

        if (!channelMemberRepository.existsByChannelIdAndUserId(channelId, currentUser.getId())) {
            throw new IllegalStateException("해당 채널에 가입되어 있지 않습니다.");
        }

        Message message = Message.builder()
                .sender(currentUser)
                .channel(channel)
                .receiverUser(null)
                .messageType(MessageType.CHANNEL)
                .content(request.content())
                .read(false)
                .build();

        Message saved = messageRepository.save(message);
        return messageMapper.toResponse(saved);
    }
}
