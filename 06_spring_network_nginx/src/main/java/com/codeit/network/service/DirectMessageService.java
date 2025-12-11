package com.codeit.network.service;

import com.codeit.network.dto.message.MessageCreateRequest;
import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.entity.Message;
import com.codeit.network.entity.MessageType;
import com.codeit.network.entity.User;
import com.codeit.network.event.DMCreatedEvent;
import com.codeit.network.mapper.MessageMapper;
import com.codeit.network.repository.MessageRepository;
import com.codeit.network.repository.UserRepository;
import com.codeit.network.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
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
    private final ApplicationEventPublisher eventPublisher;

    // 1) 내게 온 DM 전체 조회 (HTTP 초기 로딩)
    @Transactional
    public List<MessageResponse> getInbox() {
        User currentUser = getCurrentUserEntity();
        Long currentUserId = currentUser.getId();

        List<Message> messages = messageRepository
                .findByMessageTypeAndReceiverUserIdOrderByIdAsc(
                        MessageType.DIRECT,
                        currentUserId
                );

        if (!messages.isEmpty()) {
            boolean changed = false;
            for (Message m : messages) {
                if (m.getReceiverUser() != null
                        && m.getReceiverUser().getId().equals(currentUserId)
                        && !m.isRead()) {
                    m.markAsRead();
                    changed = true;
                }
            }
            if (changed) {
                messageRepository.saveAll(messages);
            }
        }

        return messages.stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    // 2) DM 전송 (STOMP에서 호출)
    @Transactional
    public MessageResponse sendDirectMessageFromStomp(
            Long receiverUserId,
            MessageCreateRequest request,
            Long senderUserId
    ) {
        if (senderUserId == null) {
            throw new IllegalStateException("보낸 사람 ID가 없습니다.");
        }

        User sender = userRepository.findById(senderUserId)
                .orElseThrow(() -> new IllegalStateException("보낸 사용자를 찾을 수 없습니다. id=" + senderUserId));

        User receiver = userRepository.findById(receiverUserId)
                .orElseThrow(() -> new IllegalArgumentException("수신 사용자를 찾을 수 없습니다. id=" + receiverUserId));

        Message message = Message.builder()
                .sender(sender)
                .receiverUser(receiver)
                .channel(null)
                .messageType(MessageType.DIRECT)
                .content(request.content())
                .read(false)
                .build();

        Message saved = messageRepository.save(message);
        MessageResponse response = messageMapper.toResponse(saved);

        eventPublisher.publishEvent(new DMCreatedEvent(receiverUserId, response));

        return response;
    }

    /**
     * SecurityContext 에서 현재 사용자 ID를 꺼내서 실제 User 엔티티로 조회
     */
    private User getCurrentUserEntity() {
        var context = SecurityContextHolder.getContext();
        if (context == null) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Object principal = authentication.getPrincipal();
        Long userId;

        if (principal instanceof MyUserDetails myUserDetails) {
            userId = myUserDetails.getUserDto().id();
        } else if (principal instanceof User user) {
            // 혹시 일부 경로에서 여전히 User 가 principal 인 경우 대비
            userId = user.getId();
        } else {
            throw new IllegalStateException("로그인 정보가 올바르지 않습니다.");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다. id=" + userId));
    }
}
