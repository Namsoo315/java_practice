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


    // 채널 입장 시 전체 대화 (또는 나중에 limit 걸어도 됨)
    public List<MessageResponse> getChannelMessages(Long channelId) {
        User currentUser = getCurrentUser();

        if (!channelMemberRepository.existsByChannelIdAndUserId(channelId, currentUser.getId())) {
            throw new IllegalArgumentException("채널에 가입되지 않았습니다.");
        }

        List<Message> messages = messageRepository
                .findByMessageTypeAndChannelIdOrderByIdAsc(MessageType.CHANNEL, channelId);

        return messages.stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    // Polling: lastMessageId 이후 새 채널 메시지
    public List<MessageResponse> pollChannelMessages(Long channelId, Long lastMessageId) {
        User currentUser = getCurrentUser();
        if (!channelMemberRepository.existsByChannelIdAndUserId(channelId, currentUser.getId())) {
            throw new IllegalArgumentException("채널에 가입되지 않았습니다.");
        }

        List<Message> messages = messageRepository
                .findByMessageTypeAndChannelIdAndIdGreaterThanOrderByIdAsc(
                        MessageType.CHANNEL, channelId, lastMessageId != null ? lastMessageId : 0L);

        return messages.stream()
                .map(messageMapper::toResponse)
                .toList();
    }



    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User user)) {
            throw new IllegalStateException("로그인 정보가 올바르지 않습니다.");
        }
        return user;
    }

    @Transactional
    public MessageResponse sendChannelMessage(Long channelId, MessageCreateRequest request) {
        User currentUser = getCurrentUser();

        // 채널 존재 여부 확인
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("요청하신 채널을 찾을 수 없습니다."));

        // 채널 가입 여부 확인
        boolean isMember = channelMemberRepository.existsByChannelIdAndUserId(channelId, currentUser.getId());
        if (!isMember) {
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
