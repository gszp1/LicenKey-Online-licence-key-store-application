#!/bin/bash

docker stop licen_key_db
sleep 1

docker rm licen_key_db
sleep 1

docker image rm licen_key_db
sleep 1

docker build -t licen_key_db .

docker run --name licen_key_db -d -p 5432:5432 licen_key_db