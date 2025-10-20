package com.data.mongo.mongo.document;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "posts")
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class PostDocument {

  @Id
  private String id;

  private String title;

  private String content;

  private List<String> tags;

  private String category;

  @Field("author_id")
  private String authorId;

  @CreatedDate
  @Field("created_at")
  private String createdAt;

  @LastModifiedDate
  @Field("updated_at")
  private String updatedAt;

}
