package com.codeit.graphql.service;


import com.codeit.graphql.dto.comment.CommentCreateRequest;
import com.codeit.graphql.dto.comment.CommentDto;
import com.codeit.graphql.dto.comment.CommentPageDto;
import com.codeit.graphql.entity.Comment;
import com.codeit.graphql.entity.Post;
import com.codeit.graphql.entity.User;
import com.codeit.graphql.mapper.CommentMapper;
import com.codeit.graphql.repository.CommentRepository;
import com.codeit.graphql.repository.PostRepository;
import com.codeit.graphql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public CommentDto getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        return commentMapper.toDto(comment);
    }

    @Transactional(readOnly = true)
    public CommentPageDto getComments(Pageable pageable) {
        Page<Comment> page = commentRepository.findAll(pageable);
        return commentMapper.toPageDto(page);
    }

    @Transactional(readOnly = true)
    public CommentPageDto searchByContent(String keyword, Pageable pageable) {
        Page<Comment> page = commentRepository.findByContentContainingIgnoreCase(keyword, pageable);
        return commentMapper.toPageDto(page);
    }

    @Transactional(readOnly = true)
    public CommentPageDto searchByAuthorNickName(String nickName, Pageable pageable) {
        Page<Comment> page = commentRepository.findByAuthorNickNameContainingIgnoreCase(nickName, pageable);
        return commentMapper.toPageDto(page);
    }

    @Transactional
    public CommentDto createComment(CommentCreateRequest request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .post(post)
                .author(author)
                .content(request.content())
                .isDeleted(false)
                .build();

        Comment saved = commentRepository.save(comment);
        return commentMapper.toDto(saved);
    }

    @Transactional
    public boolean deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        comment.setDeleted(true);
        return true;
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return commentMapper.toDtoList(comments);
    }
}
