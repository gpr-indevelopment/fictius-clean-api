FROM openjdk:11-jdk-slim-buster

COPY ./target/api-1.0.0.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Xmx256m", "api-1.0.0.jar"]