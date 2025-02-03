#!/bin/bash

./gradlew clean build -x test

docker rm order-function
sleep 1

docker rm image key-generation-service
sleep 1

docker build -t key-generation-service .
