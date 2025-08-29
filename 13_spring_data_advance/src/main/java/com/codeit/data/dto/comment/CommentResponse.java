package com.codeit.data.dto.comment;

import com.codeit.data.entity.Post;
import com.codeit.data.entity.User;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
  private Long id;
  private String content;
  private Instant createdAt;

  private User author;
  private Post post;
}
