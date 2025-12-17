package com.codeit.elastic.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.codeit.elastic.document.PostDocument;
import com.codeit.elastic.dto.common.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostSearchService {

    private final ElasticsearchTemplate template;

    public PagedResult<PostDocument> searchContent(String keyword, int page, int size) {
        Pageable pageable = pageable(page, size);   // 0 index 기반

        NativeQuery query = NativeQuery.builder()
            .withQuery(
                q -> q.match(
                    m -> m.field("content")
                        .query(keyword)
//                        .minimumShouldMatch("70%")
                ))
            .withPageable(pageable)
            .withSort(s -> s.field(f -> f
                .field("createdAt")
                .order(SortOrder.Desc)
            ))
//            .withTrackScores(true)      // 유사도 점수 (정렬 기준에 1순위로 유사도를 둬도 됨)
            .build();

        SearchHits<PostDocument> result = template.search(query, PostDocument.class);
        result.getSearchHits().forEach(hit -> {
//            System.out.println("score = " + hit.getScore());
            System.out.println("doc = " + hit.getContent());
        });

        return toPagedResult(result, page, size);
    }


    public PagedResult<PostDocument> searchAuthor(String keyword, int page, int size) {
        Pageable pageable = pageable(page, size);   // 0 index 기반

        NativeQuery query = NativeQuery.builder()
            .withQuery(
                q -> q.multiMatch(
                    mm -> mm
                        .query(keyword)
                        .fields("authorUsername", "authorNickname")
                ))
            .withPageable(pageable)
            .withSort(s -> s.field(f -> f
                .field("createdAt")
                .order(SortOrder.Desc)
            )).build();

        SearchHits<PostDocument> result = template.search(query, PostDocument.class);
        result.getSearchHits().forEach(System.out::println);

        return toPagedResult(result, page, size);
    }

    public PagedResult<PostDocument> searchAll(String keyword, int page, int size) {
        Pageable pageable = pageable(page, size); // 0 index 기반

        NativeQuery query = NativeQuery.builder()
            .withQuery(q -> q.multiMatch(
                mm-> mm.
                    query(keyword)
                    .fields("title", "content",
                        "tags","category","authorUsername","authorNickname")
            ))
            .withPageable(pageable)
            .withSort(s -> s.field(f->f.
                field("createdAt")
                .order(SortOrder.Desc)
            )).build();

        SearchHits<PostDocument> result = template.search(query, PostDocument.class);
        // 결과값 프린트
        result.getSearchHits().forEach(System.out::println);
        return toPagedResult(result, pageable.getPageNumber(), pageable.getPageSize());

    }

    private Pageable pageable(int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50); // 실습용 상한 50
        return PageRequest.of(safePage, safeSize);
    }

    private <T> PagedResult<T> toPagedResult(SearchHits<T> hits, int page, int size) {
        List<T> items = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();

        return new PagedResult<>(hits.getTotalHits(), page, size, items);
    }
}
