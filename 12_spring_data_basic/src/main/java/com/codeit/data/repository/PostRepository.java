package com.codeit.data.repository;

import com.codeit.data.entity.Category;
import com.codeit.data.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

  // 제목, 내용, 태그 부분
  List<Post> findByTitleContaining(String title); // like %title%

  List<Post> findByContentContaining(String content);

  List<Post> findByTagsContainingIgnoreCase(String tags); //Tag가 무시도 되는 케이스

  // 카테고리 별 블로그 찾기
  List<Post> findByCategory(Category category);

  List<Post> findByCategoryIn(List<Category> categories);

  // 작성자 ID로 블로그 찾기
  List<Post> findByAuthorId(Long id);

  List<Post> findByAuthor_Id(Long id);

  List<Post> findByAuthor_IdOrderByIdDesc(Long id); // 정렬

  List<Post> findByAuthor_NicknameContaining(String nickname);

  // JPQL 실습
  // -> 쿼리 메서드 이름과 겹치지 않게끔 만드는게 유리하다.
  // 쿼리는 모두 소문자로 작성된다!!! -> 테이블이 아닌 Entity를 타겟팅 한다.

  // 작성자 이름(username)으로 게시글 검색
//  @Query("select p from Post p where p.content = :content and p.author = :test")  //인자가 1개 필요할 때
  @Query("""
        select p
        from Post p
        join p.author u
        where u.username = :username
      """)
  List<Post> findPostsByUsername(@Param("username") String username);

  // 제목 like 절 + 페이징 처리 (억지로 JPQL로 해야하는 것도 있다.)
  @Query("""
          select p
          from Post p
          where p.title like %:keyword% or p.content like %:content%
      """)
  List<Post> findPageByTitleAndContent(@Param("title") String title,
      @Param("content") String content, Sort sort);  //Sort만 집어넣으면 바로 정렬이 됨.

  // 모든 조건 AND절 검색 (title, content, nickname) + 페이징 처리 ★★★★★ -> 쿼리메서드로 해결이 어렵다.
  @Query("""
        select p
        from Post p
        join p.author u
        where (:title is null or p.title like %:title%)
        and (:content is null or p.content like %:content%)
        and (:nickname is null or u.nickname like %:nickname%)
      
      """)
  Page<Post> searchPost(@Param("title") String title,
      @Param("content") String content,
      @Param("nickname") String nickname,
      Pageable pageable);

  // Native SQL
  // 사용자와 게시글 JOIN 하여 조회
  @Query(value = """
      select p.*
      from posts p
      join users u on p.author_id = u.id
      where u.username = :username
      """, nativeQuery = true)
  List<Post> findPostsByUsernameNative(@Param("username") String username);
}
