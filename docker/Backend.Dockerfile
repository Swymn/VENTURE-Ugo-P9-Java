## Stage 1
FROM maven:3.9 AS builder

WORKDIR /app

COPY . .

WORKDIR /app/medilab
RUN mvn -pl backend -am clean package -DskipTests

## Stage 2
FROM openjdk:21-jdk

WORKDIR /app

COPY --from=builder /app/medilab/backend/target/*.jar app.jar

EXPOSE 8080

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://127.0.0.1:5432/medilab
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=rootroot

ENTRYPOINT ["java", "-jar", "app.jar"]