package com.codeit.blog.service;

import com.codeit.blog.dto.base.SlicePageResponse;
import com.codeit.blog.dto.comment.CommentCreateRequest;
import com.codeit.blog.dto.comment.CommentFlatResponse;
import com.codeit.blog.dto.comment.CommentResponse;
import com.codeit.blog.dto.comment.CommentSearchRequest;
import com.codeit.blog.entity.Comment;
import com.codeit.blog.entity.Post;
import com.codeit.blog.entity.User;
import com.codeit.blog.mapper.CommentMapper;
import com.codeit.blog.mapper.PageResponseMapper;
import com.codeit.blog.repository.CommentRepository;
import com.codeit.blog.repository.PostRepository;
import com.codeit.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final PageResponseMapper pageResponseMapper;

    // 댓글 등록
    @Transactional
    public CommentResponse saveComment(CommentCreateRequest request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new NoSuchElementException("post not found: " + request.postId()));
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new NoSuchElementException("user not found: " + request.authorId()));

        Comment comment = commentMapper.toEntity(request, author, post);
        Comment saved = commentRepository.save(comment);

        return commentMapper.toResponse(saved);
    }

    // 댓글 단건 조회 (Entity → Response DTO)
    @Transactional(readOnly = true)
    public CommentResponse findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("comment not found: " + id));
        return commentMapper.toResponse(comment);
    }

    // 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> findAll() {
        return commentMapper.toResponseList(commentRepository.findAll());
    }

    // 특정 블로그에 달린 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> findByPostId(Long postId) {
        return commentMapper.toResponseList(commentRepository.findByPostId(postId));
    }

    // 특정 작성자가 단 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> findByAuthorId(Long authorId) {
        return commentMapper.toResponseList(commentRepository.findByAuthorId(authorId));
    }

    // 댓글 삭제
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    // slice 적용 예제
    @Transactional(readOnly = true)
    public SlicePageResponse<CommentResponse> findAllSlice(int size, Long cursor) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));

        Slice<Comment> slice = (cursor == null)
                ? commentRepository.findAllByOrderByIdDesc(pageable)
                : commentRepository.findAllByIdLessThanOrderByIdDesc(cursor, pageable);

        List<CommentResponse> items = commentMapper.toResponseList(slice.getContent());
        return pageResponseMapper.toSlice(slice, items);
    }


    @Transactional(readOnly = true)
    public SlicePageResponse<CommentFlatResponse> find(CommentSearchRequest req) {
        if ((req.postId() == null) == (req.userId() == null)) {
            throw new IllegalArgumentException("postId 또는 userId 중 하나만 지정해야 합니다.");
        }

        Sort.Direction sortDir = (req.direction() == com.querydsl.core.types.Order.ASC)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        int page = (req.cursor() != null && req.cursor() >= 0) ? req.cursor() : 0;
        int size = (req.limit() != null && req.limit() > 0) ? req.limit() : 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, "id"));

        Slice<CommentFlatResponse> slice =
                (req.postId() != null)
                        ? commentRepository.findFlatByPostId(req.postId(), pageable)
                        : commentRepository.findFlatByUserId(req.userId(), pageable);

        return pageResponseMapper.toSlice(slice);
    }

}