FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache dependencies first
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Build the application
COPY src ./src
RUN mvn -q -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

RUN groupadd --system appgroup && useradd --system --gid appgroup appuser

COPY --from=build /app/target/*.jar /app/app.jar
RUN chown appuser:appgroup /app/app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70", "-XX:+UseSerialGC", "-jar", "app.jar"]
