
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/lib/*.jar /libs/

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"] 