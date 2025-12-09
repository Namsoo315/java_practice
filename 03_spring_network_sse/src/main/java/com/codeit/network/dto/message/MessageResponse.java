package com.codeit.network.dto.message;

import com.codeit.network.entity.MessageType;

import java.time.Instant;

public record MessageResponse(
        Long id,
        MessageType messageType,
        Long channelId,
        Long senderId,
        String senderNickname,
        Long receiverUserId,
        String content,
        boolean read,
        Instant createdAt
) {}

