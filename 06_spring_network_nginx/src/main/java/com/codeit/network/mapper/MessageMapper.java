package com.codeit.network.mapper;

import com.codeit.network.dto.message.MessageResponse;
import com.codeit.network.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "channelId", source = "channel.id")
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderNickname", source = "sender.nickname")
    @Mapping(target = "receiverUserId", source = "receiverUser.id")
    com.codeit.network.dto.message.MessageResponse toResponse(Message message);
}
