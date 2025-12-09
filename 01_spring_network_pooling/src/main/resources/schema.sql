-- =====================================
-- 기존 테이블 제거
-- =====================================
DROP TABLE IF EXISTS messages CASCADE;
DROP TABLE IF EXISTS channel_members CASCADE;
DROP TABLE IF EXISTS channels CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- =====================================
-- users 테이블 (전역 역할 추가)
-- =====================================
CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    nickname   VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    role       VARCHAR(20)  NOT NULL DEFAULT 'USER',  -- USER / ADMIN
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =====================================
-- channels 테이블
-- =====================================
CREATE TABLE IF NOT EXISTS channels
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_private  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =====================================
-- channel_members 테이블 (채널 내 역할은 member_role로 분리)
-- =====================================
CREATE TABLE IF NOT EXISTS channel_members
(
    id           BIGSERIAL PRIMARY KEY,
    channel_id   BIGINT      NOT NULL,
    user_id      BIGINT      NOT NULL,
    member_role  VARCHAR(20) NOT NULL DEFAULT 'MEMBER', -- OWNER / MEMBER
    joined_at    TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_channel_members_channel
        FOREIGN KEY (channel_id) REFERENCES channels (id) ON DELETE CASCADE,
    CONSTRAINT fk_channel_members_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,

    CONSTRAINT uq_channel_members UNIQUE (channel_id, user_id)
);

-- =====================================
-- messages 테이블 (DM + 채널 메시지 통합)
-- =====================================
CREATE TABLE IF NOT EXISTS messages
(
    id               BIGSERIAL PRIMARY KEY,
    sender_id        BIGINT      NOT NULL,
    receiver_user_id BIGINT,               -- DIRECT일 경우 사용
    channel_id       BIGINT,               -- CHANNEL일 경우 사용
    message_type     VARCHAR(20) NOT NULL, -- DIRECT / CHANNEL
    content          TEXT        NOT NULL,
    is_read          BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at       TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_messages_sender
        FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE,

    CONSTRAINT fk_messages_receiver_user
        FOREIGN KEY (receiver_user_id) REFERENCES users (id) ON DELETE CASCADE,

    CONSTRAINT fk_messages_channel
        FOREIGN KEY (channel_id) REFERENCES channels (id) ON DELETE CASCADE
);
