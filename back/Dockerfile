# Use Maven image to build the project
FROM maven:3.9.4-eclipse-temurin-17 AS mavenBuilder

# Set the working directory
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY src ./src

# Build the project with Maven
RUN mvn clean package -DskipTests

# Use a lightweight JDK base image for the runtime
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory for the runtime
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=mavenBuilder /app/target/*.jar build.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "build.jar"]