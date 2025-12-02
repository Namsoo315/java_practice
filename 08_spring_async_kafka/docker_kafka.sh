# Kafka 최신 버전 실행 (포트 9092)
docker run -d --name broker -p 9092:9092 apache/kafka:latest

# Kafka 확인
docker ps

# Kafka 중지
docker stop broker

# Kafka 삭제
docker rm broker

# Kafka 로그 확인
docker logs -f broker

# Kafka CLI 접속
docker exec -it broker bash
