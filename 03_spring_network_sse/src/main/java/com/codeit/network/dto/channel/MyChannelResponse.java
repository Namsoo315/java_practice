package com.codeit.network.dto.channel;



import com.codeit.network.entity.ChannelMemberRole;

import java.time.Instant;

public record MyChannelResponse(
        Long id,
        String name,
        String description,
        boolean privateChannel,
        ChannelMemberRole memberRole,
        Instant joinedAt
) {}
