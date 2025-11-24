package com.codeit.jwt.service;


import com.codeit.jwt.dto.post.PostCreateRequest;
import com.codeit.jwt.dto.post.PostDto;
import com.codeit.jwt.dto.post.PostUpdateRequest;
import com.codeit.jwt.entity.Post;
import com.codeit.jwt.entity.User;
import com.codeit.jwt.exception.PostNotFoundException;
import com.codeit.jwt.exception.UserNotFoundException;
import com.codeit.jwt.mapper.PostMapper;
import com.codeit.jwt.repository.PostRepository;
import com.codeit.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostDto create(PostCreateRequest request, Long authorId) {
        log.debug("포스트 생성 시작: title={}, authorId={}", request.title(), authorId);

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + authorId + " not found"));

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .deleted(false)
                .build();

        postRepository.save(post);

        log.info("포스트 생성 완료: id={}, title={}", post.getId(), post.getTitle());
        return postMapper.toDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> findAllVisible() {
        log.debug("공개 포스트 목록 조회");
        List<PostDto> posts = postRepository.findAllVisiblePostsWithAuthor()
                .stream()
                .map(postMapper::toDto)
                .toList();
        log.info("공개 포스트 목록 조회 완료: {}개", posts.size());
        return posts;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<PostDto> findAll() {
        log.debug("전체 포스트 목록 조회");
        List<PostDto> posts = postRepository.findAllPostsWithAuthor()
                .stream()
                .map(postMapper::toDto)
                .toList();
        log.info("전체 포스트 목록 조회 완료: {}개", posts.size());
        return posts;
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long postId) {
        log.debug("포스트 조회: id={}", postId);
        PostDto post = postRepository.findById(postId)
                .map(postMapper::toDto)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + postId + " not found"));
        log.info("포스트 조회 완료: id={}", postId);
        return post;
    }

    // SpEL에서 사용할 소유자 확인 헬퍼
    @Transactional(readOnly = true)
    public boolean isOwner(Long postId, Long userId) {
        return postRepository.findById(postId)
                .map(Post::getAuthor)
                .map(User::getId)
                .filter(userId::equals)
                .isPresent();
    }

    @PreAuthorize("hasRole('ADMIN') or @postService.isOwner(#postId, principal.userDto.id)")
    @Transactional
    public PostDto update(Long postId, PostUpdateRequest request) {
        log.debug("포스트 수정 시작: id={}", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + postId + " not found"));

        if (request.newTitle() != null) post.setTitle(request.newTitle());
        if (request.newContent() != null) post.setContent(request.newContent());
        post.setUpdatedAt(Instant.now());

        log.info("포스트 수정 완료: id={}", postId);
        return postMapper.toDto(post);
    }

    // 논리 삭제 토글
    @PreAuthorize("hasRole('ADMIN') or @postService.isOwner(#postId, principal.userDto.id)")
    @Transactional
    public PostDto toggleDeleted(Long postId) {
        log.debug("포스트 삭제 토글 시작: id={}", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with id " + postId + " not found"));

        post.setDeleted(!post.isDeleted());
        post.setUpdatedAt(Instant.now());

        log.info("포스트 삭제 토글 완료: id={}, deleted={}", postId, post.isDeleted());
        return postMapper.toDto(post);
    }

}
