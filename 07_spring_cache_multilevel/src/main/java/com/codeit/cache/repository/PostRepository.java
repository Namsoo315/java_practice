package com.codeit.cache.repository;

import com.codeit.cache.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByDeletedFalse(Pageable pageable);

    @Query("""
           select p
           from Post p
           where p.deleted = false
             and (:title  is null or lower(p.title)   like lower(concat('%', :title, '%')))
             and (:content is null or lower(p.content) like lower(concat('%', :content, '%')))
           """)
    Page<Post> searchByTitleAndContent(
            @Param("title") String title,
            @Param("content") String content,
            Pageable pageable
    );
}
