
# Docker Compose 전체 빌드 + 실행
docker compose up --build


# Docker Compose 백그라운드 실행
docker compose up -d --build


# 전체 서비스 로그 보기 (follow)
docker compose logs -f


# backend 서비스 로그만 보기
docker compose logs -f backend


# nginx(reverse-proxy) 로그만 보기
docker compose logs -f reverse-proxy


# 현재 실행 중인 컨테이너 확인
docker ps


# 모든 서비스 중지
docker compose down


# 컨테이너/네트워크/볼륨 모두 삭제
# (초기화 수준)
docker compose down --volumes --remove-orphans
