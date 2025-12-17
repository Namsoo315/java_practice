#!/bin/bash

# ============================================
# Elasticsearch + Kibana 실습용 실행 스크립트
# ============================================

# [1] Docker Compose 실행
# Elasticsearch(9200) + Kibana(5601) 컨테이너를 백그라운드로 실행한다
docker compose up -d


# [2] 실행 중인 컨테이너 상태 확인
# elasticsearch, kibana 컨테이너가 정상 실행 중인지 확인한다
docker compose ps


# [3] Elasticsearch 로그 확인
# Elasticsearch 기동 및 클러스터 상태를 확인할 수 있다
docker compose logs -f elasticsearch


# [4] Kibana 로그 확인
# Kibana가 Elasticsearch에 정상 연결되었는지 확인한다
docker compose logs -f kibana


# [5] 컨테이너 중지 및 제거
# 실습 종료 후 컨테이너만 정리한다 (데이터 유지)
docker compose down


# [6] 컨테이너 + 볼륨 전체 삭제
# Elasticsearch 데이터까지 완전히 초기화한다
docker compose down -v


# Elasticsearch 접속
# http://localhost:9200

# Kibana 접속
# http://localhost:5601

# 클러스터 상태 확인
# curl http://localhost:9200/_cluster/health