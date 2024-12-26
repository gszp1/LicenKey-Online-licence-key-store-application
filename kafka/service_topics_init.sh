#!/bin/bash

/opt/bitnami/kafka/bin/kafka-topics.sh --create --if-not-exists --topic example --bootstrap-server kafka:9092 --partitions 1 --replication-factor 1