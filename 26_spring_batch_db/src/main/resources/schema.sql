-- h2 DB 활용시 자동으로 추가될 영역

-- DROP DATABASE IF EXISTS my_blog;
-- CREATE DATABASE my_blog ENCODING = 'UTF8';

DROP TABLE IF EXISTS user_login_logs;
DROP TABLE IF EXISTS system_login_stats;
DROP TABLE IF EXISTS users;

-- users 테이블
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50)              NOT NULL UNIQUE,
    password   VARCHAR(200)             NOT NULL,
    email      VARCHAR(100)             NOT NULL UNIQUE,
    nickname   VARCHAR(50)              NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE          DEFAULT now()
);

CREATE TABLE user_login_logs
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id) ON DELETE CASCADE,
    login_time  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    success     BOOLEAN                  NOT NULL, -- 로그인 성공 여부
    fail_reason VARCHAR(200),                      -- 실패 원인 (예: INVALID_PASSWORD)
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);


CREATE TABLE IF NOT EXISTS system_login_stats
(
    id                   INT PRIMARY KEY CHECK (id = 1),
    total_users          BIGINT NOT NULL DEFAULT 0,
    total_login_attempts BIGINT NOT NULL DEFAULT 0,
    total_success_logins BIGINT NOT NULL DEFAULT 0,
    last_login_time      TIMESTAMP WITH TIME ZONE
);
