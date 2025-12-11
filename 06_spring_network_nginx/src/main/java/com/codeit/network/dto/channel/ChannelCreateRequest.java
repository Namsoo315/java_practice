package com.codeit.network.dto.channel;

public record ChannelCreateRequest(
        String name,
        String description,
        boolean privateChannel
) {}