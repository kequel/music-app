FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# install Maven i curl (healthcheck)
RUN apk add --no-cache maven curl

# Kopiuj pliki projektu
COPY pom.xml .
COPY src src

# Zbuduj projekt
RUN mvn clean package -DskipTests

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "target/eureka-service-1.0-SNAPSHOT.jar"]