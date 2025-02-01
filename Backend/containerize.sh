#!/bin/bash

docker rm licen-key-backend
sleep 1

docker image rm licen-key-backend
sleep 1

docker build -t licen-key-backend .

