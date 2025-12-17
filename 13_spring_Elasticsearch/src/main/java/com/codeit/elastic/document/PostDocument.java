package com.codeit.elastic.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;


@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(indexName = "posts")
public class PostDocument {

  @Id
  private String id; // ES 문서의 고유 ID (UUID 등)

  private Long postId;  // RDB의 PK

  private String title;
  private String content;
  private String tags;
  private String category;

  // 유저 정보 flat하게 설계
  private Long authorId;
  private String authorUsername;
  private String authorNickname;

  private Instant createdAt;
}
