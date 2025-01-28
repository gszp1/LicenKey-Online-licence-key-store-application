#!/bin/bash

docker rm licen-key-backend
sleep 1

docker image rm licen-key-backend
sleep 1

docker build -t licen-key-backend .

docker run --name licen-key-backend -d -p 9000:9000 licen-key-backend

