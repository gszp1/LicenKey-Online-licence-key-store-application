#!/bin/bash

docker build -t sa-frontend-i .

docker run --name sa-frontend-c -d -p 9091:9091 sa-frontend-i 