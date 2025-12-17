package com.codeit.elastic.event;

import com.codeit.elastic.document.PostDocument;
import com.codeit.elastic.event.EventType;
import com.codeit.elastic.event.PostIndexEvent;
import com.codeit.elastic.mapper.PostMapper;
import com.codeit.elastic.repository.PostQueryRepository;
import com.codeit.elastic.repository.elastic.PostElasticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
public class PostElasticEventListener {

    private final PostQueryRepository postQueryRepository;
    private final PostMapper mapper;
    private final PostElasticRepository postElasticRepository;

    @Async("elasticAsyncExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PostIndexEvent event) {

        // DELETE
        if (event.type() == EventType.DELETE) {
            postElasticRepository.deleteByPostId(event.postId());
            return;
        }

        // UPSERT
        PostDocument doc = postQueryRepository.findOneForIndexing(event.postId())
                .map(mapper::toDocument)
                .orElse(null);

        if (doc == null) {
            postElasticRepository.deleteByPostId(event.postId());
            return;
        }

        // 기존 문서가 있으면 ES document id 유지
        postElasticRepository.findFirstByPostId(event.postId())
                .ifPresent(existing -> doc.setId(existing.getId()));

        postElasticRepository.save(doc);
    }
}
