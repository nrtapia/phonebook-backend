FROM openjdk:8-jdk-alpine3.8
ENV _JAVA_OPTIONS "-Xms100m -Xmx200m -Djava.awt.headless=true"

# Maintainer
MAINTAINER "neider.tapia@gmail.com"

VOLUME /tmp
EXPOSE 5000
COPY target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app/app.jar"]

# docker build -t phonebook-backend .
