package com.codeit.blog.entity.common;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @CreatedDate
    @Column(columnDefinition = "timestamp with time zone default now()",
                updatable = false, nullable = false)
    private Instant createdAt;


    @LastModifiedDate
    @Column(name = "updated_at",
            nullable = false,
            columnDefinition = "timestamp with time zone default now()")
    private Instant updatedAt;
}