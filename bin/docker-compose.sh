#!/bin/bash

cd Kafka
bin/kafka-connector-build.sh

docker-compose up --build
