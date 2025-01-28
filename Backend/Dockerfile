FROM ubuntu:24.04

RUN mkdir -p /backend-app && \
    mkdir -p /var/logs/backend

RUN chmod 755 /var/logs/backend

WORKDIR /backend-app

RUN apt-get update && \
    apt-get install -y wget unzip openjdk-21-jdk && \
    rm -rf /var/lib/apt/lists/*

RUN wget https://services.gradle.org/distributions/gradle-8.10-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-8.10-bin.zip && \
    ln -s /opt/gradle/gradle-8.10/bin/gradle /usr/bin/gradle

COPY . .

RUN gradle build --no-daemon -x test

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/app/build/libs/backend-1.0.0.jar"]