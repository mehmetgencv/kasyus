#FROM maven:3.9.9-eclipse-temurin-21 AS build
#
#WORKDIR /app
#
#COPY pom.xml .
#COPY src/main/java/com/kasyus/product_service/ProductServiceApplication.java ./src/main/java/com/kasyus/product_service/ProductServiceApplication.java
#
#RUN mvn clean package -DskipTests
#
#COPY src ./src

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"] 