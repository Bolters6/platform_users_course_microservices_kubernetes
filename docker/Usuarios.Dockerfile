#FROM openjdk:17-alpine
#WORKDIR /app
#COPY ../microservicio_cursos/target/microservicio_cursos-0.0.1-SNAPSHOT.jar .cursos.jar
#EXPOSE 8081
#ENTRYPOINT["java", "-jar", "cursos.jar"]
#PACKAGE STAGE
FROM maven:3.8.4-openjdk-17-slim as builder
ARG DIR_NAME=msvc-usuarios
WORKDIR /app/$DIR_NAME

COPY ../microservicio_usuarios/pom.xml .

RUN mvn clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ../microservicio_usuarios/src ./src

RUN mvn clean install -DskipTests

#BUILD STAGE
FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-usuarios/target/microservicio_usuarios-0.0.1-SNAPSHOT.jar usuarios.jar
ARG PORT_APP=8081
ENV PORT $PORT_APP
ENV DB_HOST host.docker.internal:3306
ENV DB_NAME usuariosdb
ENV DB_USER root
ENV DB_PASS mario639
ENV REST_TEMPLATE_HOST host.docker.internal
EXPOSE $PORT
#PERO ES RECOMENDABLE QUE CUANDO HAY MUCHOS ENV CREAR MEJOR EL FILE .env del proyecto
#ESTOS ARGUMENTOS SOLO SE PUEDEN PONER EN EL DOCKER FILE Y ES COMO CREAR VARIABLES PARA CUANDO REPITES MUCHO EN EL DOCKERFILE
#TAMBIEN DAN FLEXIBILIDAD EN LA HORA DE HACER LA BUILD QUE PUEDES POR LINEA DE COMANDO ESCRIBIR --build-arg PORT_APP=8081 y asi en la build sobreescribes ese ARG
#PUEDO TAMBIEN HACER UN FILE .env UNICO DE CONFIGURACION ENV DONDE TENGO MIS VARIABLES DE AMBIENTE ALLI TODAS LAS QUE NECESITO Y CUANDO LANZO EL DOCKER RUN LLAMAR ESE ARCHIVO
#O TAMBIEN PUEDO ESCRIBIR LAS VARIABLES DE ENTORNO EN LA LINEA DE COMANDO DOCKER CON -e o --env
#ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "usuarios.jar"] ESTO SI TENGO MAS DE UN APPLICATION.PROPERTIES OSEA OTROS PROFILE
ENTRYPOINT ["java", "-jar", "usuarios.jar"]



