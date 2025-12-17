package com.codeit.elastic.service;

import com.codeit.elastic.dto.PostCreateRequest;
import com.codeit.elastic.dto.PostResponse;
import com.codeit.elastic.dto.PostUpdateRequest;
import com.codeit.elastic.entity.Post;
import com.codeit.elastic.entity.User;
import com.codeit.elastic.event.EventType;
import com.codeit.elastic.event.PostIndexEvent;
import com.codeit.elastic.mapper.PostMapper;
import com.codeit.elastic.repository.PostRepository;
import com.codeit.elastic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PostMapper mapper;

    @Transactional
    public PostResponse create(PostCreateRequest request) {
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new IllegalArgumentException("author not found"));

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .tags(request.tags())
                .category(request.category())
                .author(author)
                .build();

        Post saved = postRepository.save(post);

        eventPublisher.publishEvent(
                new PostIndexEvent(saved.getId(), EventType.UPSERT)
        );

        return mapper.toPostResponse(saved);
    }

    @Transactional(readOnly = true)
    public PostResponse get(Long postId) {
        Post post = findActive(postId);
        return mapper.toPostResponse(post);
    }

    @Transactional
    public PostResponse update(Long postId, PostUpdateRequest request) {
        Post post = findActive(postId);

        post.setTitle(request.title());
        post.setContent(request.content());
        post.setTags(request.tags());
        post.setCategory(request.category());

        eventPublisher.publishEvent(
                new PostIndexEvent(post.getId(), EventType.UPSERT)
        );

        return mapper.toPostResponse(post);
    }

    @Transactional
    public PostResponse delete(Long postId) {
        Post post = findActive(postId);

        post.markDeleted();

        eventPublisher.publishEvent(
                new PostIndexEvent(postId, EventType.DELETE)
        );

        return mapper.toPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        return postRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(mapper::toPostResponse)
                .toList();
    }

    private Post findActive(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post not found"));
        if (post.isDeleted()) {
            throw new IllegalStateException("post is deleted");
        }
        return post;
    }


}
