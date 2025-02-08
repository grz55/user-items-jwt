FROM maven:3.9.6-eclipse-temurin-21 AS builder
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /target/*.jar user-items-jwt.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user-items-jwt.jar"]