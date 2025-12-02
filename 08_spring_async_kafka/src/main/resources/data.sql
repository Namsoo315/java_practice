-------------------------------------------
-- users: 테스트용 사용자 2명
-------------------------------------------
INSERT INTO users (username, email, password, role, address)
VALUES ('user1', 'user1@email.com', '1234', 'USER', '서울시 강남구 어딘가');

INSERT INTO users (username, email, password, role, address)
VALUES ('admin', 'admin@example.com', 'admin', 'ADMIN', '서울시 서초구 어딘가');
-------------------------------------------
-- products: 아이폰 / 맥북 / 갤럭시 10개
-------------------------------------------
INSERT INTO products (name, description, price, quantity)
VALUES ( 'iPhone 16 128GB', '최신 아이폰 16 128GB 모델', 1500000, 10);

INSERT INTO products (name, description, price, quantity)
VALUES ( 'iPhone 16 Pro 256GB', '아이폰 16 프로 256GB 모델', 1900000, 5);

INSERT INTO products (name, description, price, quantity)
VALUES ( 'iPhone SE 128GB', '가성비 아이폰 SE 128GB', 650000, 20);

INSERT INTO products (name, description, price, quantity)
VALUES ( 'MacBook Air 13 M2', '맥북 에어 13인치 M2 칩', 1500000, 8);

INSERT INTO products (name, description, price, quantity)
VALUES ( 'MacBook Pro 14 M3', '맥북 프로 14인치 M3 칩', 2800000, 4);

INSERT INTO products (name, description, price, quantity)
VALUES  ('Galaxy S24 256GB', '삼성 갤럭시 S24 256GB', 1200000, 15);

INSERT INTO products (name, description, price, quantity)
VALUES ( 'Galaxy S24 Ultra 512GB', '삼성 갤럭시 S24 울트라 512GB', 1600000, 7);

INSERT INTO products (name, description, price, quantity)
VALUES ('Galaxy Z Flip6 256GB', '갤럭시 Z 플립6 256GB', 1500000, 6);

INSERT INTO products (name, description, price, quantity)
VALUES ( 'Galaxy Z Fold6 512GB', '갤럭시 Z 폴드6 512GB', 2400000, 3);

INSERT INTO products (name, description, price, quantity)
VALUES ( 'Galaxy Tab S9 256GB', '갤럭시 탭 S9 256GB', 1100000, 12);
