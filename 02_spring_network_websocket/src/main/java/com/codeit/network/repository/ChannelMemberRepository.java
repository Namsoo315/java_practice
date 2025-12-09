package com.codeit.network.repository;


import com.codeit.network.entity.ChannelMember;
import com.codeit.network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    boolean existsByChannelIdAndUserId(Long channelId, Long userId);

    Optional<ChannelMember> findByChannelIdAndUserId(Long channelId, Long userId);

    List<ChannelMember> findByUserId(Long userId);
}
