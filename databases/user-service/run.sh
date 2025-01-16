#!/bin/bash

docker build -t licen_key_db .

docker run --name licen_key_db -d -p 5432:5432 licen_key_db