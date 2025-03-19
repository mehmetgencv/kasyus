FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/lib/*.jar /libs/

COPY target/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"] 