package com.codeit.network.dto.channel;

import java.time.Instant;

public record ChannelResponse(
        Long id,
        String name,
        String description,
        boolean privateChannel,
        Instant createdAt
) {}
