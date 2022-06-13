FROM openjdk:11-jdk-slim
COPY src/main/resources/local .
ARG WORKSPACE=/home
ARG SPRING_PROFILES_ACTIVE

WORKDIR ${WORKSPACE}

COPY . .
RUN ./gradlew clean bootJar
WORKDIR ${WORKSPACE}/build/libs
RUN cp *.jar app.jar

ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENTRYPOINT ["java","-jar","./app.jar"]
EXPOSE 8080