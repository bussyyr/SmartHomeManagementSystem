
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/jpa-persistency-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
