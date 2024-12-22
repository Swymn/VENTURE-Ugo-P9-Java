## Stage 1
FROM maven:3.9 AS builder

WORKDIR /app

COPY . .

WORKDIR /app/medilab

RUN mvn -pl gateway -am clean package -DskipTests

## Stage 2
FROM openjdk:21-jdk

WORKDIR /app

COPY --from=builder /app/medilab/gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]