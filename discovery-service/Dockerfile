## Use Maven to build the application
#FROM maven:3.9.9-eclipse-temurin-21 AS build
#
## Set the working directory
#WORKDIR /app
#
## Copy the pom.xml and source code
#COPY pom.xml .
#COPY src ./src
#
## Package the application
#RUN mvn clean package -DskipTests

# Use Eclipse Temurin JRE to run the application
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the application JAR file from the build stage
COPY target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8761

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]