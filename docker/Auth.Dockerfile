#FROM openjdk:17-alpine
#WORKDIR /app
#COPY ../msvc-auth/target/msvc-auth-0.0.1-SNAPSHOT.jar .cursos.jar
#EXPOSE 8081
#ENTRYPOINT["java", "-jar", "cursos.jar"]
#PACKAGE STAGE
FROM maven:3.8.6-openjdk-18 as builder

WORKDIR /app/msvc-auth

COPY ../msvc-auth/pom.xml .

RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ../msvc-auth/src ./src

RUN mvn clean install -DskipTests

#BUILD STAGE
FROM openjdk:17

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-auth/target/msvc-auth-0.0.1-SNAPSHOT.jar auth.jar
#ARG PORT_APP=8081
#ENV PORT $PORT_APP
#ENV DB_HOST host.docker.internal:3306
#ENV DB_NAME cursosdb
#ENV DB_USER root
#ENV DB_PASS mario639
#ENV REST_TEMPLATE_HOST host.docker.internal
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "auth.jar"]



