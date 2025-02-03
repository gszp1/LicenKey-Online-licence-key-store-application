FROM openjdk:21-slim

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport"

RUN mkdir -p /app

WORKDIR /app

COPY ./build/libs/key-function.jar .

EXPOSE 9003

ENTRYPOINT [ "java", "-jar", "/app/key-function.jar" ]