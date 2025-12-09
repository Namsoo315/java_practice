package com.codeit.network.event;


import com.codeit.network.repository.ChannelMemberRepository;
import com.codeit.network.sse.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelMessageCreatedEventListener {

    private final ChannelMemberRepository  channelMemberRepository;
    private final SseService sseService;

    // TransactionalEventListener를 쓸때 @Async는 AFTER_COMMIT과 AFTER_COMPLETION만 사용할수 있다!
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onChannelMessageCreated(ChannelMessageCreatedEvent event) {
        Long channelId = event.channelId();

        // 채널 멤버의 전체 userId를 조회
        List<Long> userIds = channelMemberRepository.findUserIdsByChannelId(channelId);
        if(userIds == null || userIds.isEmpty()){
            return;
        }
        
        Long senderId = event.message().senderId();
        
        // 보낸 사람을 제외하고 수신자 집합 구성
        Set<Long> receiverIds = userIds.stream()
                .filter(userId -> !userId.equals(senderId))
                .collect(Collectors.toSet());

        if(receiverIds.isEmpty()){
            return;
        }

        log.info("[Channel Event] channelId={} sender={} receivers={} messageId={}",
                channelId, senderId, receiverIds, event.message().id());

        sseService.send(
                receiverIds, // 받을 ids
                "channel", // 이벤트 이름
                event.message()); // 전달 data
    }

}
