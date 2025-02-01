#!/bin/bash

docker rm licen_key_db
sleep 1

docker image rm licen_key_db
sleep 1

docker build -t licen_key_db .
