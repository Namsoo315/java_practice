package com.codeit.batch.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "system_login_stats")
public class SystemLoginStats {
    @Id
    private Integer id;                      // 항상 1

    @Column(name = "total_users", nullable = false)
    private long totalUsers;

    @Column(name = "total_login_attempts", nullable = false)
    private long totalLoginAttempts;

    @Column(name = "total_success_logins", nullable = false)
    private long totalSuccessLogins;

    @Column(name = "last_login_time")
    private Instant lastLoginTime;
}
