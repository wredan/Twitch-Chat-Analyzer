#!/bin/bash
docker stop twitch-spark
docker container rm twitch-spark

bin/deploy.sh

docker run -it --network twitch-chat-analyzer_twitch --ip 10.0.100.43 -p 9092:9092 --name twitch-spark spark:consumer 
