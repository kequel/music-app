FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY pom.xml .
COPY src src

RUN apk add --no-cache maven && \
    mvn clean package -DskipTests && \
    rm -rf ~/.m2

EXPOSE 8080
CMD ["java", "-jar", "target/gateway-service-1.0-SNAPSHOT.jar"]