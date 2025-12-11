# 빌드 스테이지
FROM amazoncorretto:17 AS builder

WORKDIR /app

COPY gradle ./gradle
COPY gradlew ./gradlew
COPY build.gradle settings.gradle ./

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

COPY src ./src
RUN ./gradlew clean build -x test --no-daemon


# 런타임 스테이지
FROM amazoncorretto:17-alpine3.21

WORKDIR /app

ENV JVM_OPTS=""

# 실행 가능한 boot JAR만 app.jar로 복사
COPY --from=builder /app/build/libs/*SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar /app/app.jar"]
