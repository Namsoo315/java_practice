package com.codeit.batch.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "user_login_logs")
public class UserLoginLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "login_time", nullable = false)
    private Instant loginTime;

    @Column(nullable = false)
    private boolean success;

    @Column(name = "fail_reason", length = 200)
    private String failReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
