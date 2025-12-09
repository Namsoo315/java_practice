package com.codeit.network.service;

import com.codeit.network.dto.message.MessageCreateRequest;
import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.entity.Message;
import com.codeit.network.entity.MessageType;
import com.codeit.network.entity.User;
import com.codeit.network.mapper.MessageMapper;
import com.codeit.network.repository.MessageRepository;
import com.codeit.network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    /**
     * 내게 온 DM 전체 조회
     * - 화면 처음 들어올 때 사용
     * - 조회한 메시지는 읽음 처리
     */
    @Transactional
    public List<MessageResponse> getInbox() {
        User currentUser = getCurrentUser();

        List<Message> messages = messageRepository
                .findByMessageTypeAndReceiverUserIdOrderByIdAsc(
                        MessageType.DIRECT,
                        currentUser.getId()
                );

        markAsRead(messages, currentUser.getId());

        return messages.stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    /**
     * Polling / Long Polling 공용:
     * 내게 온 DM 중 lastMessageId 이후만 조회
     * - 조회한 메시지는 읽음 처리
     */
    @Transactional
    public List<MessageResponse> pollInbox(Long lastMessageId) {
        User currentUser = getCurrentUser();
        long cursor = (lastMessageId != null ? lastMessageId : 0L);

        List<Message> messages = messageRepository
                .findByMessageTypeAndReceiverUserIdAndIdGreaterThanOrderByIdAsc(
                        MessageType.DIRECT,
                        currentUser.getId(),
                        cursor
                );

        markAsRead(messages, currentUser.getId());

        return messages.stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    /**
     * DM 전송
     * - sender: 현재 로그인 사용자
     * - receiver: path variable로 받은 사용자 ID
     */
    @Transactional
    public MessageResponse sendDirectMessage(Long receiverUserId, MessageCreateRequest request) {
        User sender = getCurrentUser();

        User receiver = userRepository.findById(receiverUserId)
                .orElseThrow(() -> new IllegalArgumentException("수신 사용자를 찾을 수 없습니다."));

        Message message = Message.builder()
                .sender(sender)
                .receiverUser(receiver)
                .channel(null)
                .messageType(MessageType.DIRECT)
                .content(request.content())
                .read(false)
                .build();

        Message saved = messageRepository.save(message);
        return messageMapper.toResponse(saved);
    }

    /**
     * 조회한 DM을 읽음 처리
     * - 내게 온 메시지이면서 아직 안 읽은 것만 대상으로 처리
     */
    private void markAsRead(List<Message> messages, Long currentUserId) {
        if (messages == null || messages.isEmpty()) {
            return;
        }

        boolean changed = false;
        for (Message m : messages) {
            // 혹시 모를 안전 장치: 진짜 "나에게 온 메시지"인지 확인
            if (m.getReceiverUser() != null
                    && m.getReceiverUser().getId().equals(currentUserId)
                    && !m.isRead()) {   // boolean 필드가 isRead 라고 가정
                m.markAsRead();
                changed = true;
            }
        }

        // JPA 영속성 컨텍스트에 올라온 엔티티라면 saveAll 없어도 되지만,
        // 수업용 예제이므로 명시적으로 saveAll 처리
        if (changed) {
            messageRepository.saveAll(messages);
        }
    }

    /**
     * SecurityContext에서 현재 로그인된 User 엔티티 꺼내기
     */
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (!(principal instanceof User user)) {
            throw new IllegalStateException("로그인 정보가 올바르지 않습니다.");
        }
        return user;
    }
}
