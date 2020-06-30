#!/bin/bash

cd Kafka
bin/kafka-connector-build.sh

cd ../Spark/Python
bin/spark-build.sh

docker-compose up --build
