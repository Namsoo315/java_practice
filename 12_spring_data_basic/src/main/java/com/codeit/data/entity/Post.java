package com.codeit.data.entity;

import com.codeit.data.entity.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "posts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter @SuperBuilder @ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

  @Column(nullable = false, length = 50)
  private String title;

  @Column(nullable = false, length = 50)
  private String content;

  @Column(length = 50)
  private String tags;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private Category category;

  // JPA에서 관계를 가지는 객체 설계하기
  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)  // 외래키를 지정하는 어노테이션
  private User author;

  // 댓글 -> 비주인일때 댓글을 가져오는 방법
  @OneToMany(mappedBy = "post")
  private List<Comment> comments = new ArrayList<>();
}
