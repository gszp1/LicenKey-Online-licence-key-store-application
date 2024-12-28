#!/bin/bash

docker build -t users_service .

docker run --name users_service -d -p 9091:9091 users_service