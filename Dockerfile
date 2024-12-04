FROM openjdk:21-jdk
WORKDIR /app
COPY target/parking_reservation-0.0.1-SNAPSHOT.jar /app/parking_reservation-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/parking_reservation-0.0.1-SNAPSHOT.jar"]
