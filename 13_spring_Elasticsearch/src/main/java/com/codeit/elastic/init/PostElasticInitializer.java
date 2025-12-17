package com.codeit.elastic.init;

import com.codeit.elastic.document.PostDocument;
import com.codeit.elastic.dto.PostSearchRow;
import com.codeit.elastic.mapper.PostMapper;
import com.codeit.elastic.repository.PostQueryRepository;
import com.codeit.elastic.repository.elastic.PostElasticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostElasticInitializer {

    private final PostQueryRepository postQueryRepository;
    private final PostMapper postSearchMapper;
    private final PostElasticRepository postElasticRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(readOnly = true)
    public void init() {

        // 1. Elasticsearch 기존 데이터 전체 삭제
        postElasticRepository.deleteAll();

        // 2. RDB에서 색인 대상 데이터 조회
        List<PostSearchRow> rows = postQueryRepository.findAllForIndexing();

        // 3. RDB → Elasticsearch Document 변환
        List<PostDocument> documents = rows.stream()
                .map(postSearchMapper::toDocument)
                .toList();

        // 4. Elasticsearch 저장 (초기 색인)
        postElasticRepository.saveAll(documents);
    }
}
