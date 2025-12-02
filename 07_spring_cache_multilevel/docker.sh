# Redis 최신 버전 실행 (포트 6379)
docker run -d --name redis-local -p 6379:6379 redis:7-alpine

# Redis 확인
docker ps

# Redis 중지
docker stop redis-local

# Redis 삭제
docker rm redis-local

# Redis 로그 확인
docker logs -f redis-local

# Redis CLI 접속
docker exec -it redis-local redis-cli