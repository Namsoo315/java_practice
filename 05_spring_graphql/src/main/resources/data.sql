INSERT INTO users (id, username, email, password, nick_name, role, status)
VALUES (1, 'user1', 'user1@codeit.com', '1234', '스프링입문러', 'USER', 'ACTIVE'),
       (2, 'user2', 'user2@codeit.com', '1234', 'JPA덕후개발자', 'USER', 'ACTIVE'),
       (3, 'user3', 'user3@codeit.com', '1234', 'GraphQL호기심천국', 'USER', 'ACTIVE'),
       (4, 'user4', 'user4@codeit.com', '1234', '테스트코드장인', 'USER', 'ACTIVE'),
       (5, 'admin', 'admin@codeit.com', 'admin', '스프링관리자', 'ADMIN', 'ACTIVE');

INSERT INTO posts (author_id, title, content, is_published, is_deleted)
VALUES (1,
        '스프링 입문: Spring Boot로 첫 블로그 만들기',
        '이 글에서는 스프링(Spring)과 Spring Boot를 이용해 간단한 블로그 백엔드를 만드는 방법을 정리한다. H2 DB와 JPA를 이용해 빠르게 개발 환경을 구성하고, 이후 PostgreSQL로도 확장할 수 있다.',
        TRUE, FALSE),

       (1,
        'Spring과 스프링 DI/IoC 핵심 개념 정리',
        '스프링 프레임워크(Spring Framework)의 DI와 IoC 컨테이너 개념을 정리한다. Bean 등록, 의존성 주입, 컴포넌트 스캔을 통해 스프링 애플리케이션 구조를 단순화할 수 있다.',
        TRUE, FALSE),

       (2,
        'Spring Data JPA로 게시글/댓글 관계 매핑하기',
        '이 글에서는 스프링(Spring) 환경에서 Spring Data JPA를 사용해 users, posts, comments 테이블을 어떻게 엔티티로 매핑할지 예제로 다룬다.',
        TRUE, FALSE),

       (2,
        '스프링 트랜잭션 전파와 롤백 전략',
        '스프링(Spring) 트랜잭션 추상화와 전파 속성을 이해하면 게시글 저장과 댓글 저장을 하나의 트랜잭션으로 묶어 일관성을 보장할 수 있다.',
        TRUE, FALSE),

       (2,
        'Spring Security로 로그인과 권한(ADMIN/USER) 구현',
        'Spring Security를 이용하여 USER와 ADMIN 역할을 분리하고, 스프링 기반 GraphQL API에 인증과 인가를 적용하는 기본 방법을 설명한다.',
        TRUE, FALSE),

       (3,
        '스프링에서 H2와 PostgreSQL 프로파일 전환하기',
        '개발 환경에서는 H2를 사용하고 운영에서는 PostgreSQL을 사용하는 스프링(Spring) 프로파일 구성을 예제로 정리한다.',
        TRUE, FALSE),

       (3,
        'GraphQL vs REST: 스프링 백엔드에서 어떻게 선택할까',
        '스프링(Spring) Boot 기반 백엔드에서 REST와 GraphQL을 비교한다. 오버페칭과 언더페칭 문제를 GraphQL로 어떻게 개선하는지 예제로 보인다.',
        TRUE, FALSE),

       (3,
        'Spring Boot에서 GraphQL Resolver 설계 패턴',
        '스프링(Spring)에서 GraphQL Query와 Mutation 리졸버를 Service 계층과 분리해 설계하는 패턴을 소개한다.',
        TRUE, FALSE),

       (4,
        '스프링 테스트 코드로 리팩토링을 안전하게 하기',
        '스프링(Spring) 애플리케이션에서 JUnit과 Spring Boot Test를 활용해 서비스 리팩토링을 안전하게 하는 방법을 정리한다.',
        TRUE, FALSE),

       (4,
        'Spring Batch로 대용량 데이터 처리 초입문',
        '스프링(Spring Batch)을 이용하여 로그나 통계 데이터를 배치로 처리하는 기초 내용을 다룬다.',
        TRUE, FALSE),

       (4,
        '스프링 캐시와 Redis로 조회 성능 최적화',
        '스프링(Spring) Cache 추상화와 Redis를 이용해 게시글 목록 조회 성능을 개선하는 전략을 살펴본다.',
        TRUE, FALSE),

       (5,
        'Spring Cloud와 마이크로서비스 개요',
        'Spring Cloud를 활용하여 서비스 디스커버리, 구성 서버, 게이트웨이 패턴을 이해하고, 스프링(Spring) 기반 마이크로서비스 구조를 설명한다.',
        TRUE, FALSE),

       (1,
        'HTTP와 REST API 기본 개념 복습',
        'URI 설계, HTTP 메서드, 상태 코드 등 REST API를 설계할 때 알아야 할 기본 개념을 정리한 글이다.',
        TRUE, FALSE),

       (1,
        '프론트엔드와 백엔드가 협업하는 방법',
        'API 스펙 문서화, Mock 서버 활용, 스웨거 같은 도구로 협업 효율을 높이는 실무 팁을 소개한다.',
        TRUE, FALSE),

       (2,
        '테스트 주도 개발(TDD)로 코드 품질 끌어올리기',
        'TDD의 기본 흐름과 스프링 기반 서비스에 TDD를 적용할 때의 장단점을 이야기한다.',
        TRUE, FALSE),

       (2,
        'Git 브랜치 전략: Git Flow vs Trunk Based',
        '팀 개발에서 자주 사용하는 Git 브랜치 전략을 비교하고 상황별 선택 기준을 정리했다.',
        TRUE, FALSE),

       (3,
        'Docker로 스프링 애플리케이션 컨테이너라이즈 하기',
        'Spring Boot 애플리케이션을 Docker 이미지로 빌드하고 컨테이너로 실행하는 과정을 단계별로 설명한다.',
        TRUE, FALSE),

       (3,
        'CI/CD 파이프라인: GitHub Actions 예제',
        '테스트, 빌드, 배포를 자동화하는 GitHub Actions 파이프라인 구성 예제를 공유한다.',
        TRUE, FALSE),

       (4,
        'Kafka로 비동기 이벤트 발행/구독 구현',
        'Kafka 토픽 설계와 소비자 그룹 개념을 중심으로 비동기 이벤트 아키텍처를 소개한다.',
        TRUE, FALSE),

       (4,
        '실시간 채팅 시스템 설계 아이디어',
        'WebSocket, SSE, Long Polling의 차이와 채팅 시스템 설계 시 고려할 점을 정리했다.',
        TRUE, FALSE),

       (5,
        '클린 아키텍처와 계층형 아키텍처 비교',
        '도메인 중심 설계를 강조하는 클린 아키텍처와 전통적인 계층형 아키텍처의 차이를 설명한다.',
        TRUE, FALSE),

       (5,
        '코드 리뷰 문화 정착시키기',
        '개발팀에 코드 리뷰 문화를 도입할 때의 가이드라인과 안티패턴을 이야기한다.',
        TRUE, FALSE),

       (1,
        '성능 테스트 기초와 부하 테스트 도구',
        '성능 지표, 부하 테스트 시나리오, JMeter 같은 도구를 활용하는 기본 방법을 정리했다.',
        TRUE, FALSE),

       (2,
        '로그 수집과 모니터링: ELK와 프로메테우스',
        '서비스 안정성을 위해 로그와 메트릭 수집, 대시보드 구성 전략을 소개한다.',
        TRUE, FALSE),

       (3,
        '개발자 포트폴리오와 기술 블로그 운영 팁',
        '기술 블로그를 운영하면서 어떤 글을 쓸지, 어떻게 지속적으로 기록할지에 대한 개인적인 경험을 공유한다.',
        TRUE, FALSE);


-- Post 1 ~ 5
INSERT INTO comments (post_id, author_id, content, is_deleted)
VALUES (1, 2, '스프링 입문자에게 딱 좋은 글이네요.', FALSE),
       (1, 3, 'Spring Boot 설정 예제 코드도 있으면 좋겠습니다.', FALSE),
       (1, 4, 'H2로 먼저 연습해보고 싶어요.', FALSE),

       (2, 1, 'DI 개념이 아직도 헷갈렸는데 정리가 잘 됩니다.', FALSE),
       (2, 3, '스프링 IoC 컨테이너 그림이 인상적이네요.', FALSE),

       (3, 1, '관계 매핑 예제가 실제 강의에서 쓰기 좋아 보입니다.', FALSE),
       (3, 4, 'Spring Data JPA 페이징도 나중에 다뤄주세요.', FALSE),
       (3, 5, '관리자 입장에서도 이런 구조가 유지보수에 좋습니다.', FALSE),

       (4, 2, '트랜잭션 전파 옵션 정리가 깔끔하네요.', FALSE),
       (4, 3, '스프링에서 롤백 조건을 어떻게 설정하는지 알 수 있었어요.', FALSE),

       (5, 1, 'Spring Security는 항상 어렵지만 예제가 친절하네요.', FALSE),
       (5, 4, 'ADMIN/USER 역할 분리가 잘 되어 있군요.', FALSE),
       (5, 5, '운영에서 꼭 필요한 보안 기능입니다.', FALSE);

-- Post 6 ~ 10
INSERT INTO comments (post_id, author_id, content, is_deleted)
VALUES (6, 1, 'H2와 PostgreSQL 전환 전략이 유용합니다.', FALSE),
       (6, 2, '프로파일로 스프링 설정 분리하는 부분이 좋네요.', FALSE),

       (7, 3, 'GraphQL과 REST를 같이 쓰는 패턴도 궁금합니다.', FALSE),
       (7, 4, '오버페칭 문제를 실무 예제로 설명해줘서 이해가 잘 돼요.', FALSE),

       (8, 1, 'Resolver 계층을 서비스와 분리한 구조가 마음에 듭니다.', FALSE),
       (8, 2, '테스트하기도 편해 보이는 아키텍처네요.', FALSE),
       (8, 5, '운영자 입장에서 모니터링 포인트도 정리해주면 좋겠습니다.', FALSE),

       (9, 3, '테스트 코드로 스프링 리팩토링을 안전하게 할 수 있겠네요.', FALSE),
       (9, 4, '실무 예제가 있어서 바로 따라 해볼 수 있겠습니다.', FALSE),

       (10, 2, 'Batch 기초를 이해하는 데 큰 도움이 됐습니다.', FALSE),
       (10, 5, '배치 실패 시 재시도 전략도 궁금합니다.', FALSE);

-- Post 11 ~ 15
INSERT INTO comments (post_id, author_id, content, is_deleted)
VALUES (11, 1, '캐시 적용 전후 성능 비교 그래프도 있으면 좋겠어요.', FALSE),
       (11, 3, 'Redis와 스프링 캐시 연동 부분을 잘 정리해주셨네요.', FALSE),

       (12, 2, 'Spring Cloud 개요를 한 번에 볼 수 있어서 좋습니다.', FALSE),
       (12, 4, 'Config 서버와 게이트웨이까지 한 번에 다루어 주셔서 감사합니다.', FALSE),

       (13, 1, 'REST 기본 개념을 다시 정리하는 데 딱이네요.', FALSE),
       (13, 2, '상태 코드 부분만 따로 프린트해서 보고 싶습니다.', FALSE),
       (13, 3, 'HTTP 캐시 관련 내용도 추가되면 좋겠네요.', FALSE),

       (14, 4, '프론트와 백엔드 협업 팁에 공감합니다.', FALSE),
       (14, 5, '실제 코드 리뷰 예시도 있으면 좋겠습니다.', FALSE),

       (15, 2, 'TDD를 적용해 본 경험을 공유해 주셔서 유익했습니다.', FALSE),
       (15, 3, '스프링 서비스에 TDD를 도입해 보고 싶어졌어요.', FALSE),
       (15, 4, '테스트 작성 시간이 아깝지 않다는 게 인상적입니다.', FALSE);

-- Post 16 ~ 20
INSERT INTO comments (post_id, author_id, content, is_deleted)
VALUES (16, 1, 'Git Flow와 Trunk Based를 비교한 표가 좋네요.', FALSE),
       (16, 3, '팀 상황에 따라 전략을 바꾸라는 조언이 와 닿습니다.', FALSE),

       (17, 2, 'Dockerfile 예제가 실무에 바로 쓸 수 있을 정도네요.', FALSE),
       (17, 4, '스프링 이미지 최적화 팁도 유용합니다.', FALSE),
       (17, 5, '운영 환경에서 헬스체크까지 고려한 부분이 좋습니다.', FALSE),

       (18, 3, 'GitHub Actions YAML 예제를 그대로 써보려고요.', FALSE),
       (18, 1, 'CI/CD 파이프라인을 단계적으로 나눈 게 이해하기 쉽습니다.', FALSE),

       (19, 4, 'Kafka 토픽 설계 부분이 인상적입니다.', FALSE),
       (19, 2, '소비자 그룹 예시 덕분에 개념이 확실히 잡혔어요.', FALSE),

       (20, 1, '실시간 채팅 기술 비교가 아주 도움이 됩니다.', FALSE),
       (20, 3, 'WebSocket과 SSE 차이를 정리해 둔 표가 좋네요.', FALSE);

-- Post 21 ~ 25
INSERT INTO comments (post_id, author_id, content, is_deleted)
VALUES (21, 5, '클린 아키텍처 다이어그램이 보기 좋습니다.', FALSE),
       (21, 2, '계층형 아키텍처를 쓰던 팀에도 참고가 되겠어요.', FALSE),

       (22, 1, '코드 리뷰 문화에 대해 많은 생각을 하게 되네요.', FALSE),
       (22, 3, '리뷰 코멘트 예시가 실전에서 바로 써먹을 수 있습니다.', FALSE),
       (22, 4, '심리적 안전을 강조한 부분이 인상 깊습니다.', FALSE),

       (23, 2, '부하 테스트 도구를 비교해 준 점이 좋습니다.', FALSE),
       (23, 4, '성능 지표 정의 예시도 도움이 되네요.', FALSE),

       (24, 3, 'ELK와 프로메테우스를 같이 쓸 때 구조가 궁금했는데 이해했습니다.', FALSE),
       (24, 5, '운영자 입장에서 모니터링 대시보드 구성이 중요하네요.', FALSE),

       (25, 1, '기술 블로그를 계속 운영하는 동기 부여가 됩니다.', FALSE),
       (25, 2, '포트폴리오에 어떤 프로젝트를 넣을지 고민할 때 참고하겠습니다.', FALSE);

