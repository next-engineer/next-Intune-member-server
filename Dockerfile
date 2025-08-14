# 1단계: 빌드
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# gradle wrapper와 설정 먼저 복사 (캐시 이점)
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle* settings.gradle* ./

# CRLF -> LF 변환 후 실행권한 부여
RUN sed -i 's/\r$//' gradlew && chmod +x ./gradlew

# 소스 복사
COPY src ./src

# 빌드
RUN ./gradlew --no-daemon clean bootJar

# 2단계: 런타임
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]