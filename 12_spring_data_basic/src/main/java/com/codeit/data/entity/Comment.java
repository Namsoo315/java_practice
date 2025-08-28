package com.codeit.data.entity;

import com.codeit.data.entity.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "comments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter @Setter @SuperBuilder @ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)  // 외래키를 지정하는 어노테이션
  private User author;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  @JsonIgnore // json으로 만들 때 제외하는 어노테이션
  private Post post;
}
