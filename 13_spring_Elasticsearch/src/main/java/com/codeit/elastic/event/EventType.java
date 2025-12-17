package com.codeit.elastic.event;

public enum EventType {
    UPSERT,   // 생성/수정 → ES 재색인
    DELETE    // 삭제 → ES 인덱스 제거
}
