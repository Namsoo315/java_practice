package com.codeit.network.event;

import com.codeit.network.dto.message.MessageResponse;

public record ChannelMessageCreatedEvent(Long channelId, MessageResponse message) {

}
