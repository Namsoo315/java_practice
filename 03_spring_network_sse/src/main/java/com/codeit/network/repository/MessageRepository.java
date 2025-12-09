package com.codeit.network.repository;

import com.codeit.network.entity.Message;
import com.codeit.network.entity.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // 채널 전체 메시지
    List<Message> findByMessageTypeAndChannelIdOrderByIdAsc(MessageType type, Long channelId);

    // 채널 메시지 - lastId 이후 (Polling/Long Polling에서 사용)
    List<Message> findByMessageTypeAndChannelIdAndIdGreaterThanOrderByIdAsc(
            MessageType type, Long channelId, Long lastMessageId);

    // DM 전체 메시지 (내게 온 것)
    List<Message> findByMessageTypeAndReceiverUserIdOrderByIdAsc(MessageType type, Long receiverUserId);

    // DM 메시지 - lastId 이후
    List<Message> findByMessageTypeAndReceiverUserIdAndIdGreaterThanOrderByIdAsc(
            MessageType type, Long receiverUserId, Long lastMessageId);
}
