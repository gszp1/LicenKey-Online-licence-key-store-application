#!/bin/bash

docker build -t user_service_db .

docker run --name users_service_db -d -p 5432:5432 users_service_db