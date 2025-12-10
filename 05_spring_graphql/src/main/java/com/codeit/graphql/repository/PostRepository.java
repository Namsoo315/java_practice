package com.codeit.graphql.repository;

import com.codeit.graphql.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 제목 포함 검색
    @Query("""
           select p
           from Post p
           where p.isDeleted = false
             and p.isPublished = true
             and lower(p.title) like lower(concat('%', :keyword, '%'))
           """)
    Page<Post> findByTitleContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);


    // 내용 포함 검색
    @Query("""
           select p
           from Post p
           where p.isDeleted = false
             and p.isPublished = true
             and lower(p.content) like lower(concat('%', :keyword, '%'))
           """)
    Page<Post> findByContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);


    // 제목 + 내용 포함 검색
    @Query("""
           select p
           from Post p
           where p.isDeleted = false
             and p.isPublished = true
             and (
                   lower(p.title)   like lower(concat('%', :keyword, '%'))
                or lower(p.content) like lower(concat('%', :keyword, '%'))
             )
           """)
    Page<Post> findByTitleOrContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);


    // 작성자 닉네임(nick_name)으로 검색
    @Query("""
           select p
           from Post p
             join p.author a
           where p.isDeleted = false
             and p.isPublished = true
             and lower(a.nickName) like lower(concat('%', :nickName, '%'))
           """)
    Page<Post> findByAuthorNickNameContainingIgnoreCase(@Param("nickName") String nickName, Pageable pageable);
}
