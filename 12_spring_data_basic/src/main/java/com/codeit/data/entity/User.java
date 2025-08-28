package com.codeit.data.entity;

import com.codeit.data.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")  // 물리 DB와 Entity 객체명이 일치하지 않는경우 넣는 옵션
@Getter @Setter @SuperBuilder @ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

  @Column(unique = true, nullable = false, length = 50)
  private String username;

  @Column(nullable = false, length = 50)
  private String password;

  @Column(nullable = false, length = 50)
  private String email;

  @Column(nullable = false, length = 50)
  private String nickname;

}
