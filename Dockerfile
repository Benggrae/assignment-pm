FROM openjdk:11-jdk-slim AS builder

ARG WORKSPACE=/home
WORKDIR ${WORKSPACE}

COPY . .
RUN ./gradlew clean bootJar
WORKDIR ${WORKSPACE}/build/libs

RUN cp *.jar /app.jar


FROM openjdk:11-jdk-slim
ARG WORKSPACE=/home
ARG SPRING_PROFILES_ACTIVE

COPY --from=builder /app.jar /app.jar

ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
