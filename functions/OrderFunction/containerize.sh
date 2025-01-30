#!/bin/bash

docker rm order-function
sleep 1

docker image rm order-function
sleep 1

docker build -t order-function .