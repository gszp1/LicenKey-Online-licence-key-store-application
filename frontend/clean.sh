#!/bin/bash

docker stop sa-frontend-c
sleep 1

docker rm sa-frontend-c

docker image rm sa-frontend-i