package com.codeit.elastic.repository;

import com.codeit.elastic.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByDeletedAtIsNull();
}
