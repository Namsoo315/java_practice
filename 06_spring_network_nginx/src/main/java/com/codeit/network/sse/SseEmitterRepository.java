package com.codeit.network.sse;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepository {

    // key: userId, value: 해당 유저에게 연결된 emitter 리스트
    private final ConcurrentMap<Long, List<SseEmitter>> data = new ConcurrentHashMap<>();

    public SseEmitter save(Long receiverId, SseEmitter emitter) {
        data.compute(receiverId, (key, emitters) -> {
            if (emitters == null) {
                return new CopyOnWriteArrayList<>(List.of(emitter));
            } else {
                emitters.add(emitter);
                return emitters;
            }
        });
        return emitter;
    }

    public Optional<List<SseEmitter>> findByReceiverId(Long receiverId) {
        return Optional.ofNullable(data.get(receiverId));
    }

    public List<SseEmitter> findAllByReceiverIdsIn(Collection<Long> receiverIds) {
        return data.entrySet().stream()
                .filter(entry -> receiverIds.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .toList();
    }

    public List<SseEmitter> findAll() {
        return data.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public void delete(Long receiverId, SseEmitter emitter) {
        data.computeIfPresent(receiverId, (key, emitters) -> {
            emitters.remove(emitter);
            return emitters.isEmpty() ? null : emitters;
        });
    }
}
