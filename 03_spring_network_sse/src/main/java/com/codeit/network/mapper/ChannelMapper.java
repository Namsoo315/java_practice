package com.codeit.network.mapper;

import com.codeit.network.dto.channel.ChannelResponse;
import com.codeit.network.entity.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    @Mapping(target = "privateChannel", source = "privateChannel")
    ChannelResponse toResponse(Channel channel);
}