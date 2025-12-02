package com.codeit.cache.service;

import com.codeit.cache.dto.common.PageResponse;
import com.codeit.cache.dto.post.PostCreateRequest;
import com.codeit.cache.dto.post.PostDto;
import com.codeit.cache.dto.post.PostSearchRequest;
import com.codeit.cache.dto.post.PostUpdateRequest;
import com.codeit.cache.entity.Post;
import com.codeit.cache.entity.User;
import com.codeit.cache.mapper.PostMapper;
import com.codeit.cache.repository.PostRepository;
import com.codeit.cache.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    // 생성은 모두 비우기
    @Caching(evict = {
            @CacheEvict(value = "posts", key = "'all'"),
            @CacheEvict(value = "posts", allEntries = true),
            @CacheEvict(value = "postSearch", allEntries = true),
    })
    @Transactional
    public PostDto create(PostCreateRequest request) {
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .build();
        postRepository.save(post);
        return postMapper.toDto(post);
    }


    // update/delete는 동일! #id만 캐시 update 가능!
    @Caching(evict = {
            @CacheEvict(value = "posts", key = "'all'"),
            @CacheEvict(value = "posts", key = "#id"), // put으로도 가능한데, 정책 차이
            @CacheEvict(value = "postSearch", allEntries = true),
    })
    @Transactional
    public PostDto update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.setTitle(request.title());
        post.setContent(request.content());
        return postMapper.toDto(post);
    }


    // update/delete는 동일! #id만 캐시 update 가능!
    @Caching(evict = {
            @CacheEvict(value = "posts", key = "'all'"),
            @CacheEvict(value = "posts", key = "#id"),
            @CacheEvict(value = "postSearch", allEntries = true),
    })
    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.setDeleted(true);
    }


    // 단건 조회
    @Cacheable(value = "posts", key = "#id")
    @Transactional(readOnly = true)
    public PostDto findById(Long id) {
        log.info("DB에서 게시글 단건 조회 id={}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return postMapper.toDto(post);
    }

    // 전체
    @Cacheable(value = "posts", key = "'all'")
    @Transactional(readOnly = true)
    public List<PostDto> findAll() {
        log.info("DB에서 게시글 전체 조회");
        return postMapper.toDtoList(postRepository.findAll());
    }

    //    @Cacheable(value = "postSearch") // 캐시만 분리, default key를 활용하여 알아서 키 생성! (객체 대상으로)
//    @Cacheable(value = "postSearch",
//            key = "'' + #req.title() + ':' + #req.content() " +
//                    "+ ':'  + #req.page() + ':'  + #req.pageSize()") // 사용자 직접 키 조합
    @Cacheable(value = "postSearch", key = "@keyGen.hashKey(#req)") // 커스텀 유틸로 키 생성
    @Transactional(readOnly = true)
    public PageResponse<PostDto> search(PostSearchRequest req) {
        int page = req.pageOrDefault();
        int size = req.pageSizeOrDefault();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<Post> result = postRepository.searchByTitleAndContent(
                req.titleOrNull(),
                req.contentOrNull(),
                pageable
        );

        var dtoList = postMapper.toDtoList(result.getContent());

        return new PageResponse<>(
                dtoList,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
}
