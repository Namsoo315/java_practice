package com.data.mongo.mongo.document;


import java.time.Instant;
import java.time.LocalDate;
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

@Document(collection = "users")
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserDocument {

  @Id
  private String id;

  @Indexed(unique = true)
  private String username;

  private String password;

  @Indexed(unique = true)
  private String nickname;

  @Indexed(unique = true)
  private String email;

  private LocalDate birthday;

  @CreatedDate
  @Field("created_at")
  private Instant createdAt;

  @LastModifiedDate
  @Field("updated_at")
  private Instant updatedAt;
}
