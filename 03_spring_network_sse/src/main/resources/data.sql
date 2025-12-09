INSERT INTO users (username, password, nickname, email, role)
VALUES
    ('user1', '1234', '코드장인', 'user1@example.com', 'USER'),
    ('user2', '1234', '버그헌터', 'user2@example.com', 'USER'),
    ('user3', '1234', '알고리즘마스터', 'user3@example.com', 'USER'),
    ('admin', '1234', '시스템관리요정', 'admin@example.com', 'ADMIN');

INSERT INTO channels (name, description, is_private)
VALUES
    ('basic', '기본 채널입니다.', FALSE);

INSERT INTO channel_members (channel_id, user_id, member_role)
VALUES
    (1, 1, 'MEMBER'),
    (1, 2, 'MEMBER'),
    (1, 3, 'MEMBER'),
    (1, 4, 'OWNER');
