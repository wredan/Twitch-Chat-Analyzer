#!/bin/bash
bin/kafka-connector-build.sh

docker stop twitch-kafka

docker container rm twitch-kafka

docker build . --tag twitch-kafka:connector
docker run -it --network twitch-chat-analyzer_twitch --ip 10.0.100.25 -p 9092 --name twitch-kafka twitch-kafka:connector