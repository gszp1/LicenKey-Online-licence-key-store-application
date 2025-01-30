FROM openjdk:21-slim

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport"

RUN mkdir -p /function-app

WORKDIR /function-app

COPY ./build/libs/order-function.jar .

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "/function-app/order-function.jar"]