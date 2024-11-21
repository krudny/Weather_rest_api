FROM openjdk:22

WORKDIR /app

COPY build/libs/weatherApp-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]