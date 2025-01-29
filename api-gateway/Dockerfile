#FROM maven:3.9.9-eclipse-temurin-21 AS build
#
#WORKDIR /app
#
#COPY pom.xml .
#COPY src/main/java/com/kasyus/apigateway/ApiGatewayApplication.java ./src/main/java/com/kasyus/apigateway/ApiGatewayApplication.java
#
#RUN mvn clean package -DskipTests
#
#COPY src ./src

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/lib/*.jar /libs/

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"] 