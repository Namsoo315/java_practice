package com.codeit.graphql.repository;

import com.codeit.graphql.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 내용 포함 검색
    @Query("""
           select c
           from Comment c
           where c.isDeleted = false
             and lower(c.content) like lower(concat('%', :keyword, '%'))
           """)
    Page<Comment> findByContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);


    // 댓글 작성자 닉네임 검색
    @Query("""
           select c
           from Comment c
             join c.author a
           where c.isDeleted = false
             and lower(a.nickName) like lower(concat('%', :nickName, '%'))
           """)
    Page<Comment> findByAuthorNickNameContainingIgnoreCase(@Param("nickName") String nickName, Pageable pageable);

    @Query("""
           select c
           from Comment c
           where c.isDeleted = false
             and c.post.id = :postId
           order by c.createdAt asc
           """)
    List<Comment> findAllByPostId(@Param("postId") Long postId);
}
