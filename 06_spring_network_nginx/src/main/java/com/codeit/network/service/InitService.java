package com.codeit.network.service;

import com.codeit.network.entity.*;
import com.codeit.network.repository.ChannelMemberRepository;
import com.codeit.network.repository.ChannelRepository;
import com.codeit.network.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;
    private final PasswordEncoder passwordEncoder;

    // 초기 데이터 상수 정의
    private static final String DEFAULT_PASSWORD_RAW = "1234";

    private static final String USER1_USERNAME = "user1";
    private static final String USER2_USERNAME = "user2";
    private static final String USER3_USERNAME = "user3";
    private static final String ADMIN_USERNAME = "admin";

    private static final String BASIC_CHANNEL_NAME = "basic";

    /**
     * 애플리케이션 시작 시 한 번 호출해서
     * 유저, 채널, 채널멤버를 초기화하는 진입점 메서드이다.
     */
    @Transactional
    public void initAll() {
        initUsers();
        initChannels();
        initChannelMembers();
    }

    /**
     * users 테이블 초기화이다.
     * - user1, user2, user3, admin 생성
     * - 패스워드는 PasswordEncoder로 암호화한다.
     */
    private void initUsers() {
        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD_RAW);

        createUserIfNotExists(USER1_USERNAME,
                "코드장인", "user1@example.com", Role.USER, encodedPassword);

        createUserIfNotExists(USER2_USERNAME,
                "버그헌터", "user2@example.com", Role.USER, encodedPassword);

        createUserIfNotExists(USER3_USERNAME,
                "알고리즘마스터", "user3@example.com", Role.USER, encodedPassword);

        createUserIfNotExists(ADMIN_USERNAME,
                "시스템관리요정", "admin@example.com", Role.ADMIN, encodedPassword);
    }

    private void createUserIfNotExists(String username,
                                       String nickname,
                                       String email,
                                       Role role,
                                       String encodedPassword) {

        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            log.info("사용자 이미 존재: username={}, email={}", username, email);
            return;
        }

        Instant now = Instant.now();

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .email(email)
                .role(role)
                .createdAt(now)
                .updatedAt(now)
                .build();

        userRepository.save(user);
        log.info("사용자 초기화 완료: {}", username);
    }

    /**
     * channels 테이블 초기화이다.
     * - basic 채널 1개 생성
     */
    private void initChannels() {
        if (channelRepository.existsByName(BASIC_CHANNEL_NAME)) {
            log.info("기본 채널이 이미 존재한다: {}", BASIC_CHANNEL_NAME);
            return;
        }

        Instant now = Instant.now();

        Channel basic = Channel.builder()
                .name(BASIC_CHANNEL_NAME)
                .description("기본 채널입니다.")
                .privateChannel(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        channelRepository.save(basic);
        log.info("기본 채널 초기화 완료: {}", BASIC_CHANNEL_NAME);
    }

    /**
     * channel_members 테이블 초기화이다.
     * - basic 채널에 user1, user2, user3: MEMBER
     * - basic 채널에 admin: OWNER
     */
    private void initChannelMembers() {
        Channel basic = channelRepository.findByName(BASIC_CHANNEL_NAME)
                .orElseThrow(() -> new IllegalStateException("기본 채널이 존재하지 않는다: " + BASIC_CHANNEL_NAME));

        addMemberIfNotExists(basic, USER1_USERNAME, ChannelMemberRole.MEMBER);
        addMemberIfNotExists(basic, USER2_USERNAME, ChannelMemberRole.MEMBER);
        addMemberIfNotExists(basic, USER3_USERNAME, ChannelMemberRole.MEMBER);
        addMemberIfNotExists(basic, ADMIN_USERNAME, ChannelMemberRole.OWNER);
    }

    private void addMemberIfNotExists(Channel channel, String username, ChannelMemberRole memberRole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없다: " + username));

        if (channelMemberRepository.existsByChannelIdAndUserId(channel.getId(), user.getId())) {
            log.info("채널 멤버 이미 존재: channel={}, user={}", channel.getName(), username);
            return;
        }

        ChannelMember channelMember = ChannelMember.builder()
                .channel(channel)
                .user(user)
                .memberRole(memberRole)
                .joinedAt(Instant.now())
                .build();

        channelMemberRepository.save(channelMember);
        log.info("채널 멤버 초기화 완료: channel={}, user={}, role={}",
                channel.getName(), username, memberRole);
    }
}
