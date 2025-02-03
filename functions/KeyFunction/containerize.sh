#!/bin/bash

./gradlew clean build -x test

docker image rm key-function
sleep 1

docker build -t key-function .