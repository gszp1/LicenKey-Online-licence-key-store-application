FROM openjdk:21-latest

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport"

RUN mkdir -p /app

WORKDIR /app

COPY ./build/libs/key-generation-service.jar .

EXPOSE 9002

ENTRYPOINT [ "java", "-jar", "key-generation-service.jar" ]