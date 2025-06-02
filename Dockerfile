# JAR 파일 빌드
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# 의존성 캐시
COPY build.gradle settings.gradle
RUN gradle build --no-daemon || return 0

# 실제 빌드
COPY . .
RUN gradle build --no-daemon

# JRE 환경 설정
FROM openjdk:21-jdk-slim
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 오픈
EXPOSE 8080

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
