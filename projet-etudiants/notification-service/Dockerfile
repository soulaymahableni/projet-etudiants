# Étape 1 : build avec Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : image runtime légère
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# curl est nécessaire pour le healthcheck Docker
RUN apk add --no-cache curl

COPY --from=build /app/target/notification-service-1.0.0.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]