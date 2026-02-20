FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true


FROM eclipse-temurin:21-jre-ubi9-minimal
WORKDIR /APP
COPY --from=build /app/target/*.jar /app/*.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/*.jar"]