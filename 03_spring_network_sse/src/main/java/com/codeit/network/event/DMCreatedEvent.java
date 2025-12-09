package com.codeit.network.event;


import com.codeit.network.dto.message.MessageResponse;

public record DMCreatedEvent(Long receiverId, MessageResponse message) {

}

