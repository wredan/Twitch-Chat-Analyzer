#!/bin/bash
docker stop twitch-spark
docker container rm twitch-spark

bin/deploy.sh

docker run -it --network tap-project_twitch --ip 10.0.100.42 -p 9092 --name twitch-spark spark:consumer 
