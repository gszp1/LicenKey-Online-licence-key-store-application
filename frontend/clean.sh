#!/bin/bash

docker stop SA-Frontend-C
sleep 1

docker rm SA-Frontend-C

docker image rm SA-Frontend-I