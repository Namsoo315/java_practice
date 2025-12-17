package com.codeit.redis.session;

import com.codeit.redis.entity.User;
import com.codeit.redis.keystore.KeyStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MySessionManager {

    private static final long SESSION_TTL_SECONDS = 1800;
    private static final String SESSION_COOKIE_NAME = "CODEIT_SESSION_ID";

    private final KeyStore<String, MySession> sessionStore;

    public String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        MySession session = new MySession(user.getId(), user.getUsername(), Instant.now());
        sessionStore.put(
                sessionId,
                session,
                Duration.ofSeconds(SESSION_TTL_SECONDS)
        );
        return sessionId;
    }

    public Optional<MySession> getSession(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) return Optional.empty();
        return sessionStore.get(sessionId);
    }

    public void deleteSession(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) return;
        sessionStore.delete(sessionId);
    }

    public String cookieName() {
        return SESSION_COOKIE_NAME;
    }

    public int cookieMaxAgeSeconds() {
        return (int) SESSION_TTL_SECONDS;
    }
}
