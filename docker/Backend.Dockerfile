# Stage 1: Build the application
FROM maven:3.9 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the whole project to the container
COPY . .

# Build only the backend module
WORKDIR /app/medilab
RUN mvn -pl backend -am clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file for the backend module from the builder stage
COPY --from=builder /app/medilab/backend/target/*.jar app.jar

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]