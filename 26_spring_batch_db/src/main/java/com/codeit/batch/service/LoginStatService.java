package com.codeit.batch.service;

import com.codeit.batch.dto.SystemLoginStatDto;
import com.codeit.batch.entity.QUser;
import com.codeit.batch.entity.QUserLoginLog;
import com.codeit.batch.entity.SystemLoginStats;
import com.codeit.batch.repository.SystemLoginStatsRepository;
import com.codeit.batch.repository.UserLoginLogRepository;
import com.codeit.batch.repository.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginStatService {

    private final JPAQueryFactory query;
    private final UserRepository userRepository;
    private final UserLoginLogRepository userLoginLogRepository;
    private final SystemLoginStatsRepository systemLoginStatsRepository;

    private static final QUser u = QUser.user;
    private static final QUserLoginLog l = QUserLoginLog.userLoginLog;

    public SystemLoginStatDto getSystemStats() {
        return systemLoginStatsRepository.findById(1)
                .map(row -> new SystemLoginStatDto(
                        row.getTotalUsers(),
                        row.getTotalLoginAttempts(),
                        row.getTotalSuccessLogins(),
                        row.getLastLoginTime()
                ))
                .orElse(new SystemLoginStatDto(0, 0, 0, null));
    }



    public static int count = 0;
    @Transactional
    public SystemLoginStats refreshSystemStatsRow() {
        long totalUsers = Optional.ofNullable(
                query.select(u.id.count())
                        .from(u)
                        .fetchOne()
        ).orElse(0L) + count; // count는 배치 확인용 지울것!!

        long totalAttempts = Optional.ofNullable(
                query.select(l.id.count())
                        .from(l)
                        .fetchOne()
        ).orElse(0L) + count; // count는 배치 확인용 지울것!!

        long totalSuccess = Optional.ofNullable(
                query.select(l.id.count())
                        .from(l)
                        .where(l.success.isTrue())
                        .fetchOne()
        ).orElse(0L) + count; // count는 배치 확인용 지울것!!
        count++;

        Instant lastSuccessAt = query
                .select(l.loginTime.max())
                .from(l)
                .where(l.success.isTrue())
                .fetchOne(); // 없으면 null

        SystemLoginStats row = systemLoginStatsRepository.findById(1)
                .orElseGet(() -> {
                    SystemLoginStats s = new SystemLoginStats();
                    s.setId(1);
                    return s;
                });

        row.setTotalUsers(totalUsers);
        row.setTotalLoginAttempts(totalAttempts);
        row.setTotalSuccessLogins(totalSuccess);
        row.setLastLoginTime(lastSuccessAt);

        return systemLoginStatsRepository.save(row);
    }
}
