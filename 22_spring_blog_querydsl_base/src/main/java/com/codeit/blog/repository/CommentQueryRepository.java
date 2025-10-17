package com.codeit.blog.repository;

import com.codeit.blog.dto.comment.CommentFlatResponse;
import com.codeit.blog.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentQueryRepository{
    Slice<CommentFlatResponse> findFlatByPostId(Long postId, Pageable pageable);
    Slice<CommentFlatResponse> findFlatByUserId(Long userId, Pageable pageable);
}
