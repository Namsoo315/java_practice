package com.codeit.data.repository;

import com.codeit.data.entity.Category;
import com.codeit.data.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  // 특정 블로그에 달린 댓글 조회 -> Join절로 되어야 함.
  List<Comment> findByPostId(Long id);

  //특정 블로그에 카테고리로 찾아오는 방법
  List<Comment> findByPost_Category(Category post_category);

  // 특정 작성자(닉네임)가 단 댓글 조회
  List<Comment> findByAuthor_Nickname(String nickname);

  //
  List<Comment> findByAuthorId(Long authorId);

}
