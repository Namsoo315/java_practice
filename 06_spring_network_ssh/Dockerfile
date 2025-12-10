# Dockerfile
FROM eclipse-temurin:17-jdk AS app

WORKDIR /app

# 빌드된 JAR 복사
COPY build/libs/*.jar app.jar

# SSL 인증서 복사 (Spring Boot 내부 경로 동일)
COPY src/main/resources/ssl/localhost.p12 /app/ssl/localhost.p12

# 8080(HTTP) / 8443(HTTPS) 개방
EXPOSE 8080
EXPOSE 8443

# Spring Boot 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
