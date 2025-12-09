package com.codeit.network.repository;


import com.codeit.network.entity.ChannelMember;
import com.codeit.network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    boolean existsByChannelIdAndUserId(Long channelId, Long userId);

    Optional<ChannelMember> findByChannelIdAndUserId(Long channelId, Long userId);

    List<ChannelMember> findByUserId(Long userId);

    // 채널에 속한 모든 사용자 ID 조회용
    @Query("select cm.user.id from ChannelMember cm where cm.channel.id = :channelId")
    List<Long> findUserIdsByChannelId(@Param("channelId") Long channelId);
}
